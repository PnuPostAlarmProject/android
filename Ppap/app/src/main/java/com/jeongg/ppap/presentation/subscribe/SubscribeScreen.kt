package com.jeongg.ppap.presentation.subscribe

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.PButton
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.component.PTitle
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.ui.theme.Dimens
import com.jeongg.ppap.ui.theme.main_green
import com.jeongg.ppap.ui.theme.main_pink
import com.jeongg.ppap.ui.theme.shapes

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
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(bottom = 130.dp)
            ) {
                item { DefaultSubscribe() }
                item { CustomSubscribeItem() }
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
                PButton(text = stringResource(R.string.goto_home))
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
            text = stringResource(R.string.pnu_onestop)
        )
        PDivider(modifier = Modifier.padding(vertical = Dimens.PaddingSmall))
        DefaultSubscribeItem(
            image = R.drawable.pnu2,
            text = stringResource(R.string.pnu)
        )
        PDivider(modifier = Modifier.padding(vertical = Dimens.PaddingSmall))
    }
}
@Composable
fun DefaultSubscribeItem(
    @DrawableRes image: Int,
    text: String
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = text,
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(Color.Blue.copy(alpha = 0.1f), blendMode = BlendMode.Darken),
            alpha = 0.2f,
            modifier = Modifier
                .clip(MaterialTheme.shapes.large)
                .border(1.5.dp, Color.Black, MaterialTheme.shapes.large),
        )
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = Dimens.PaddingNormal)
        ) {
            Text(
                text = text,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.align(Alignment.CenterStart)
            )
            Image(
                painter = painterResource(R.drawable.checked),
                contentDescription = "checked",
                modifier = Modifier
                    .size(31.dp)
                    .align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
fun CustomSubscribeItem(
    text: String = "정보컴퓨터공학부",
){
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.displayLarge
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            PButton(
                painter = R.drawable.edit,
                text = stringResource(R.string.edit),
                color = main_pink,
                modifier = Modifier.width(120.dp).height(36.dp),
                shape = shapes.medium
            )
            PButton(
                painter = R.drawable.remove,
                text = stringResource(R.string.remove),
                color = main_green,
                modifier = Modifier.width(120.dp).height(36.dp),
                shape = shapes.medium
            )
            Image(
                painter = painterResource(R.drawable.unchecked),
                contentDescription = "checked",
                modifier = Modifier.size(31.dp)
            )
        }
        PDivider(modifier = Modifier.padding(bottom = 12.dp))
    }

}
