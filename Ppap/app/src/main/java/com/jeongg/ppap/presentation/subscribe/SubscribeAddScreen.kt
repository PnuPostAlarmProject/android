package com.jeongg.ppap.presentation.subscribe

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.PButton
import com.jeongg.ppap.presentation.component.PTextField
import com.jeongg.ppap.presentation.component.PTitle
import com.jeongg.ppap.presentation.component.addFocusCleaner
import com.jeongg.ppap.ui.theme.bright_yellow
import com.jeongg.ppap.ui.theme.main_yellow
import com.jeongg.ppap.ui.theme.shapes
import com.jeongg.ppap.ui.theme.typography

@Composable
fun SubscribeAddScreen(
    navController: NavController
){
    val focusManager = LocalFocusManager.current
    PTitle(
        modifier = Modifier.addFocusCleaner(focusManager),
        title = stringResource(R.string.add_subscribe_title),
        description = stringResource(R.string.add_subscribe_description)
    ){
        LazyColumn {
            item { PHorizontalPager() }
            item { PTextFields() }
            item { Spacer(modifier = Modifier.height(44.dp))}
            item { PButton(text = stringResource(R.string.add_subscribe_button_text),
                onClick = {navController.navigateUp()}) }
        }
    }
}

@Composable
fun PTextFields() {
    Column {
        Row(
            modifier = Modifier.padding(top = 25.dp, bottom = 5.dp)
        ) {
            Text(text = stringResource(R.string.subscribe_field_title), style = typography.displayLarge)
            Text(text = "*", style = typography.displayLarge, color = Color.Red)
        }
        PTextField(placeholder = stringResource(R.string.subscribe_field_title_hint))

        Text(
            text = stringResource(R.string.subscribe_field_link),
            style = typography.displayLarge,
            modifier = Modifier.padding(top = 20.dp, bottom = 5.dp)
        )
        PTextField(placeholder = stringResource(R.string.subscribe_field_link_hint))

        Row(
            modifier = Modifier.padding(top = 20.dp, bottom = 5.dp)
        ) {
            Text(text = stringResource(R.string.subscribe_field_rss), style = typography.displayLarge)
            Text(text = "*", style = typography.displayLarge, color = Color.Red)
        }
        PTextField(placeholder = stringResource(R.string.subscribe_field_rss_hint))
    }

}

@Composable
fun TextDescription(){

    Box(modifier = Modifier
        .padding(10.dp)
        .fillMaxWidth()) {
        Column {
            Text(
                text = stringResource(R.string.explain1),
                style = typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 20.dp)
            )
            Text(
                text = stringResource(R.string.explain2),
                style = typography.bodyMedium,
                color = Color.Black,
                modifier = Modifier.padding(end = 80.dp)
            )
        }
        Image(
            painter = painterResource(R.drawable.pineapple),
            contentDescription = "pine_apple",
            modifier = Modifier
                .size(80.dp)
                .align(Alignment.BottomEnd)
        )
    }
}
@Composable
fun ImageDescription() {
    Image(
        painter = painterResource(R.drawable.explain),
        contentDescription = "explain_subscribe",
        modifier = Modifier
            .padding(10.dp)
            .height(120.dp),
        contentScale = ContentScale.Crop,
    )
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PHorizontalPager() {
    //val pages = listOf(TextDescription(), ImageDescription())
    val state = rememberPagerState(initialPage = 0) { 2 }

    HorizontalPager(
        state = state,
        pageSpacing = 15.dp
    ) { index ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 160.dp)
                .clip(shapes.medium)
                .background(bright_yellow),
            verticalArrangement = Arrangement.Center
        ){
            // cards
            when (index) {
                0 -> TextDescription()
                1 -> ImageDescription()
            }
            // horizontal indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(2) { iteration ->
                    val color = if (state.currentPage == iteration) main_yellow else Color.White.copy(alpha = 0.5f)
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 2.dp, vertical = 5.dp)
                            .clip(CircleShape)
                            .background(color)
                            .size(6.dp)
                    )
                }
            }
        }
    }
}