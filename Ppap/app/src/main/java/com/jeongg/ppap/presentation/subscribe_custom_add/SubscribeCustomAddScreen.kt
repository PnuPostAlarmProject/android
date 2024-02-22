package com.jeongg.ppap.presentation.subscribe_custom_add

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.PButton
import com.jeongg.ppap.presentation.component.PTextFieldCard
import com.jeongg.ppap.presentation.component.util.LaunchedEffectEvent
import com.jeongg.ppap.presentation.component.util.addFocusCleaner
import com.jeongg.ppap.presentation.component.util.negativePadding
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.theme.Dimens
import com.jeongg.ppap.theme.bright_yellow
import com.jeongg.ppap.theme.typography

@Composable
fun SubscribeCustomAddScreen(
    navController: NavController,
    viewModel: SubscribeCustomAddViewModel = hiltViewModel()
){
    val focusManager = LocalFocusManager.current
    LaunchedEffectEvent(
        eventFlow = viewModel.eventFlow,
        onNavigate = { navController.navigate(Screen.SubscribeScreen.route) }
    )
    LazyColumn(
        modifier = Modifier.addFocusCleaner(focusManager),
        contentPadding = Dimens.ScreenPadding,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item { SubscribeCustomAddTitle() }
        item { PHorizontalPager() }
        item {
            PTextFields(
                title = viewModel.subscribe.value.title,
                onTitleChange = { viewModel.onEvent(SubscribeCustomAddEvent.EnteredTitle(it)) },
                rss = viewModel.subscribe.value.rssLink,
                onRssChange = { viewModel.onEvent(SubscribeCustomAddEvent.EnteredRssLink(it)) },
            )
        }
        item {
            Column(
                modifier = Modifier.fillMaxWidth()
            ){
                PButton(
                    text = stringResource(R.string.add_subscribe_button_text),
                    onClick = { viewModel.onEvent(SubscribeCustomAddEvent.SaveSubscribe) },
                    modifier = Modifier.fillMaxWidth().align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}

@Composable
private fun SubscribeCustomAddTitle(){
    Text(
        text = stringResource(R.string.add_subscribe_button_text),
        style = MaterialTheme.typography.titleLarge,
        modifier = Modifier.padding(bottom = 20.dp)
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun PHorizontalPager() {
    val state = rememberPagerState(initialPage = 0) { 2 }

    HorizontalPager(
        state = state,
        pageSpacing = 5.dp,
        modifier = Modifier.negativePadding(),
        contentPadding = PaddingValues(horizontal = 30.dp),
    ) { index ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 170.dp)
                .alpha(if (state.currentPage == index) 1f else 0.5f)
                .clip(MaterialTheme.shapes.medium)
                .background(bright_yellow),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
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
            ){
                PHorizontalIndicator(index == 0)
                PHorizontalIndicator(index == 1)
            }
        }
    }
}

@Composable
private fun PHorizontalIndicator(
    isSelected: Boolean = false
) {
    val color = if (isSelected) Color.White else Color.White.copy(alpha = 0.5f)
    Box(
        modifier = Modifier
            .padding(horizontal = 2.dp, vertical = 5.dp)
            .clip(CircleShape)
            .background(color)
            .size(6.dp),
    )
}

@Composable
private fun PTextFields(
    title: String = "",
    onTitleChange: (String) -> Unit = {},
    rss: String = "",
    onRssChange: (String) -> Unit = {},
) {
    Column {
        PTextFieldCard(
            title = stringResource(R.string.subscribe_field_title),
            text = title,
            onValueChange = onTitleChange,
            placeholder = stringResource(R.string.subscribe_field_title_hint)
        )
        PTextFieldCard(
            modifier = Modifier.padding(top = 10.dp, bottom = 25.dp),
            title = stringResource(R.string.subscribe_field_rss),
            text = rss,
            onValueChange = onRssChange,
            placeholder = stringResource(R.string.subscribe_field_rss_hint)
        )
    }
}

@Composable
private fun TextDescription(){
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth()
    ) {
        Column {
            Text(
                text = stringResource(R.string.explain1),
                style = typography.bodyLarge,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 40.dp)
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
private fun ImageDescription() {
    Image(
        painter = painterResource(R.drawable.rss_description),
        contentDescription = "RSS 링크 설명하는 그림",
        modifier = Modifier.padding(10.dp).height(120.dp),
        contentScale = ContentScale.Crop,
    )
}
