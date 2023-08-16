package com.jeongg.ppap.presentation.subscribe

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.PButton
import com.jeongg.ppap.presentation.component.PDialog
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.component.PTitle
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.ui.theme.Dimens
import com.jeongg.ppap.ui.theme.gray3
import com.jeongg.ppap.ui.theme.main_yellow

@Composable
fun SubscribeScreen(
    navController: NavController
){
    PTitle(
        title = stringResource(R.string.subscribe_title),
        description = stringResource(R.string.subscribe_description)
    ) {
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier.padding(bottom = 120.dp),
            ) {
                item {DefaultSubscribe()}
                item {CustomSubscribe()}
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
fun DefaultSubscribe(
    onOneStopClick: () -> Unit = {},
    onPnuClick: ()  -> Unit = {}
) {
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
    @DrawableRes image: Int,
    text: String = "",
    isSelected: Boolean = true
){
    var isChecked by remember { mutableStateOf(isSelected) }
    val borderModifier = if (isChecked) Modifier.border(3.dp, main_yellow, MaterialTheme.shapes.large) else Modifier
    val img = if (isChecked) R.drawable.checked else R.drawable.unchecked

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(90.dp)
            .clip(MaterialTheme.shapes.large)
            .clickable {
                isChecked = isChecked.not()
            }
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
                .align(Alignment.CenterStart)
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
fun CustomSubscribe(){
    Column {
        Text(
            text = "내가 추가한 구독",
            style = MaterialTheme.typography.titleSmall,
        )
        PDivider(modifier = Modifier.padding(top = 5.dp))
//        PEmptyContent(id = R.drawable.apple_gray, content = "새로 추가한\n공지사항이 없습니다",
//            modifier = Modifier.padding(30.dp))
        CustomSubscribeItem()
        CustomSubscribeItem(false)
        CustomSubscribeItem()
    }
}

@Composable
fun CustomSubscribeItem(
    isSelected: Boolean = true,
    text: String = "새콤달콤"
) {
    var isChecked by remember {mutableStateOf(isSelected) }
    val img = if (isChecked) R.drawable.checked else R.drawable.unchecked
    val textColor = if (isChecked) Color.Black else gray3
    var isDialogOpen by remember { mutableStateOf(false) }
    if (isDialogOpen){
        Log.d("tagtag", "dialg")
        Dialog(
            onDismissRequest = {isDialogOpen = isDialogOpen.not()}
        ){
            PDialog()
        }
    }
    Box(
        modifier = Modifier
            .clickable { isDialogOpen = isDialogOpen.not() }
            .padding(15.dp)
            .fillMaxWidth()
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(end = 31.dp),
            color = textColor
        )
        Image(
            painter = painterResource(img),
            contentDescription = isSelected.toString(),
            modifier = Modifier
                .size(31.dp)
                .align(Alignment.CenterEnd)
                .clickable { isChecked = isChecked.not() }
        )
    }
    PDivider()
}