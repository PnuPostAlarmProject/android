package com.jeongg.ppap.presentation.subscribe

import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.data.subscribe.dto.SubscribeGetResponseDTO
import com.jeongg.ppap.presentation.component.PButton
import com.jeongg.ppap.presentation.component.PDialog
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.component.PEmptyContent
import com.jeongg.ppap.presentation.component.PTitle
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.presentation.theme.Dimens
import com.jeongg.ppap.presentation.theme.gray3
import com.jeongg.ppap.presentation.theme.main_yellow
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.log
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SubscribeScreen(
    navController: NavController,
    onUpPress: () -> Unit = {},
    viewModel: SubscribeViewModel = hiltViewModel()
){
    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest{ event ->
            when(event){
                is PEvent.GET -> { "구독 목록 조회 성공 in Screen".log() }
                is PEvent.SUCCESS, PEvent.DELETE -> { viewModel.getSubscribes() }
                is PEvent.ERROR -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                else -> { "구독 로딩중".log() }
            }
        }
    }
    PTitle(
        title = stringResource(R.string.subscribe_title),
        description = stringResource(R.string.subscribe_description),
        onUpPress = onUpPress
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.padding(bottom = 120.dp),
            ) {
                item {DefaultSubscribe()}
                item {
                    CustomSubscribe(
                        subscribes = viewModel.customSubscribes.value,
                        onDeleteClick = { viewModel.deleteSubscribe(it) },
                        onEditClick = { navController.navigate(it) },
                        onSubscribeClick = { viewModel.updateActive(it) }
                    )
                }
            }
            Column(
                modifier = Modifier
                    .height(130.dp)
                    .align(Alignment.BottomCenter)
            ) {
                PButton(
                    text = stringResource(R.string.add_subscribe),
                    color = Color.White,
                    modifier = Modifier.padding(vertical = Dimens.PaddingSmall),
                    onClick = {navController.navigate(Screen.SubscribeAddScreen.route)}
                )
                PButton(
                    text = stringResource(R.string.goto_home),
                    onClick = {navController.navigate(Screen.NoticeListScreen.route)}
                )
            }
        }
    }
}
@Composable
fun DefaultSubscribe() {
    Column {
        DefaultSubscribeItem(
            image = R.drawable.pnu1,
            text = stringResource(R.string.pnu_onestop),
        )
        Spacer(modifier = Modifier.height(10.dp))
        DefaultSubscribeItem(
            image = R.drawable.pnu2,
            text = stringResource(R.string.pnu)
        )
        Spacer(modifier = Modifier.height(Dimens.PaddingSmall))
    }
}
@Composable
fun DefaultSubscribeItem(
    @DrawableRes image: Int = R.drawable.pnu1,
    text: String = stringResource(R.string.pnu_onestop),
    isActive: Boolean = false
){
    var isChecked by remember { mutableStateOf(isActive) }
    val borderModifier = if (isChecked) Modifier.border(3.dp, main_yellow, MaterialTheme.shapes.large) else Modifier
    val img = if (isChecked) R.drawable.checked else R.drawable.unchecked
    val textColor = if (isChecked) MaterialTheme.colorScheme.surface else MaterialTheme.colorScheme.outlineVariant

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(MaterialTheme.shapes.large)
            .background(Color.Black.copy(0f))
            .clickable { isChecked = isChecked.not() }
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = text,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.Blue.copy(alpha = 0.1f), blendMode = BlendMode.Darken),
            alpha = 0.2f,
            modifier = borderModifier
        )
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .padding(horizontal = Dimens.PaddingNormal)
                .align(Alignment.CenterStart),
            color = textColor
        )
        Image(
            painter = painterResource(img),
            contentDescription = "checked: $isChecked",
            modifier = Modifier
                .padding(horizontal = Dimens.PaddingNormal)
                .size(31.dp)
                .align(Alignment.CenterEnd)
        )
    }
}

@Composable
fun CustomSubscribe(
    subscribes: List<SubscribeGetResponseDTO> = emptyList(),
    onDeleteClick: (Long) -> Unit = {},
    onEditClick: (String) -> Unit = {},
    onSubscribeClick: (Long) -> Unit = {}
){
    Column {
        Text(
            text = stringResource(R.string.custom_subscribes),
            style = MaterialTheme.typography.titleSmall,
        )
        PDivider(modifier = Modifier.padding(top = 5.dp))
        if (subscribes.isEmpty()){
            PEmptyContent(
                id = R.drawable.apple_gray, message = stringResource(R.string.empty_subscribes),
                modifier = Modifier.padding(40.dp)
            )
        }
        else {
            subscribes.forEach {
                CustomSubscribeItem(
                    text = it.title,
                    isActive = it.isActive,
                    onDeleteClick = { onDeleteClick(it.subscribeId) },
                    onEditClick = { onEditClick(Screen.SubscribeAddScreen.route + "?subscribeId=${it.subscribeId}") },
                    onSubscribeClick = { onSubscribeClick(it.subscribeId)}
                )
            }
        }
    }
}

@Composable
fun CustomSubscribeItem(
    text: String = "최강 정컴 공지",
    isActive: Boolean = true,
    onDeleteClick: () -> Unit = {},
    onEditClick: () -> Unit = {},
    onSubscribeClick: () -> Unit = {}
) {
    val img = if (isActive) R.drawable.checked else R.drawable.unchecked
    val textColor = if (isActive) MaterialTheme.colorScheme.surface else gray3
    var isDialogOpen by remember { mutableStateOf(false) }
    if (isDialogOpen){
        Dialog(
            onDismissRequest = {isDialogOpen = isDialogOpen.not()}
        ){
            PDialog(
                text = text,
                onDeleteClick = {
                    onDeleteClick()
                    isDialogOpen = isDialogOpen.not()
                },
                onEditClick = {
                    onEditClick()
                    isDialogOpen = isDialogOpen.not()
                },
                onSubscribeClick = {
                    onSubscribeClick()
                    isDialogOpen = isDialogOpen.not()
                },
                isActive = isActive
            )
        }
    }
    Box(
        modifier = Modifier
            .clickable { isDialogOpen = isDialogOpen.not() }
            .padding(15.dp)
            .fillMaxWidth()
    ){
        Box(
            modifier = Modifier
                .padding(end = 31.dp)
                .fillMaxHeight()
                .align(Alignment.CenterStart),
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(end = 24.dp),
                color = textColor,
            )
            Image(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "edit or delete",
                colorFilter = ColorFilter.tint(textColor),
                modifier = Modifier
                    .rotate(0f)
                    .align(Alignment.CenterEnd)
            )
        }
        Image(
            painter = painterResource(img),
            contentDescription = "checked: $isActive",
            modifier = Modifier
                .size(31.dp)
                .align(Alignment.CenterEnd)
                .clickable { onSubscribeClick() }
        )
    }
    PDivider()
}