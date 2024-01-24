package com.jeongg.ppap.presentation.subscribe

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.data.dto.SubscribeGetResponseDTO
import com.jeongg.ppap.presentation.component.LaunchedEffectEvent
import com.jeongg.ppap.presentation.component.PButton
import com.jeongg.ppap.presentation.component.PDialog
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.component.PEmptyContent
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.theme.Dimens
import com.jeongg.ppap.theme.gray3

@Composable
fun SubscribeScreen(
    navController: NavController,
    viewModel: SubscribeViewModel = hiltViewModel()
){
    LaunchedEffectEvent(eventFlow = viewModel.eventFlow)
    Column(
        //title = stringResource(R.string.subscribe_title)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.padding(bottom = 120.dp),
            ) {
                item {
                    CustomSubscribe(
                        subscribes = viewModel.customSubscribes.value,
                        onNavigate = { navController.navigate(Screen.SubscribeAddScreen.route) },
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
fun CustomSubscribe(
    subscribes: List<SubscribeGetResponseDTO> = emptyList(),
    onNavigate: () -> Unit = {},
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
                modifier = Modifier.padding(40.dp),
                onClick = onNavigate
            )
        }
        else {
            subscribes.forEach {
                CustomSubscribeItem(
                    text = it.title,
                    isActive = it.isActive,
                    onConfirmClick = { onDeleteClick(it.subscribeId)},//onEditClick(Screen.SubscribeAddScreen.route + "?subscribeId=${it.subscribeId}") },
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
    onConfirmClick: () -> Unit = {},
    onSubscribeClick: () -> Unit = {}
) {
    val img = if (isActive) R.drawable.checked else R.drawable.unchecked
    val textColor = if (isActive) MaterialTheme.colorScheme.surface else gray3
    val isDialogOpen = remember { mutableStateOf(false) }

    PDialog(
        text = text,
        onConfirmClick = {onConfirmClick()},
        isOpen = isDialogOpen
    )
    Box(
        modifier = Modifier
            .clickable { isDialogOpen.value = true }
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