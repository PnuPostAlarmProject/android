package com.jeongg.ppap.presentation.subscribe_add

import android.widget.Toast
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.PButton
import com.jeongg.ppap.presentation.component.PTextField
import com.jeongg.ppap.presentation.component.PTitle
import com.jeongg.ppap.presentation.component.addFocusCleaner
import com.jeongg.ppap.presentation.component.negativePadding
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.theme.bright_yellow
import com.jeongg.ppap.theme.typography
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.log
import kotlinx.coroutines.flow.collectLatest

@Composable
fun SubscribeAddScreen(
    navController: NavController,
    onUpPress: () -> Unit = {},
    viewModel: SubscribeAddViewModel = hiltViewModel()
){
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest{ event ->
            when(event){
                is PEvent.GET -> { "구독 목록 조회 성공 in Screen".log() }
                is PEvent.UPDATE, PEvent.ADD -> {
                    navController.navigate(Screen.SubscribeScreen.route)
                }
                is PEvent.ERROR -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                else -> { "구독 로딩중".log() }
            }
        }
    }
    PTitle(
        modifier = Modifier.addFocusCleaner(focusManager),
        title = stringResource(if (viewModel.isUpdate()) R.string.update_subscribe_title else R.string.add_subscribe_title),
        description = stringResource(R.string.add_subscribe_description),
        onUpPress = onUpPress
    ){
        LazyColumn {
            item { PHorizontalPager() }
            item {
                PTextFields(
                    title = viewModel.subscribe.value.title,
                    onTitleChange = { viewModel.onEvent(SubscribeAddEvent.EnteredTitle(it)) },
                    notice = viewModel.subscribe.value.noticeLink,
                    onNoticeChange = { viewModel.onEvent(SubscribeAddEvent.EnteredNoticeLink(it)) },
                    rss = viewModel.subscribe.value.rssLink,
                    onRssChange = { viewModel.onEvent(SubscribeAddEvent.EnteredRssLink(it)) },
                    enabled = !viewModel.isUpdate()
                )
            }
            item { Spacer(modifier = Modifier.height(44.dp))}
            item { PButton(
                text = stringResource(if (viewModel.isUpdate()) R.string.update_subscribe_button_text else R.string.add_subscribe_button_text),
                onClick = { viewModel.onEvent(SubscribeAddEvent.SaveSubscribe)})
            }
        }
    }
}

@Composable
fun PTextFields(
    title: String = "",
    onTitleChange: (String) -> Unit = {},
    notice: String? = "",
    onNoticeChange: (String) -> Unit = {},
    rss: String = "",
    onRssChange: (String) -> Unit = {},
    enabled: Boolean = false
) {
    Column {
        Row(
            modifier = Modifier.padding(top = 25.dp, bottom = 5.dp)
        ) {
            Text(text = stringResource(R.string.subscribe_field_title), style = typography.displayLarge)
            Text(text = "*", style = typography.displayLarge, color = Color.Red)
        }
        PTextField(
            text = title,
            onValueChange = onTitleChange,
            placeholder = stringResource(R.string.subscribe_field_title_hint)
        )

        Text(
            text = stringResource(R.string.subscribe_field_link),
            style = typography.displayLarge,
            modifier = Modifier.padding(top = 20.dp, bottom = 5.dp)
        )
        PTextField(
            text = notice ?: "",
            onValueChange = onNoticeChange,
            placeholder = stringResource(R.string.subscribe_field_link_hint)
        )

        if (enabled) {
            Row(
                modifier = Modifier.padding(top = 20.dp, bottom = 5.dp)
            ) {
                Text(
                    text = stringResource(R.string.subscribe_field_rss),
                    style = typography.displayLarge
                )
                Text(text = "*", style = typography.displayLarge, color = Color.Red)
            }
            PTextField(
                text = rss,
                onValueChange = onRssChange,
                placeholder = stringResource(R.string.subscribe_field_rss_hint)
            )
        }
    }
}

@Composable
fun TextDescription(){

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
                .defaultMinSize(minHeight = 160.dp)
                .alpha(if (state.currentPage == index) 1f else 0.5f)
                .clip(MaterialTheme.shapes.medium)
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
                    val color = if (state.currentPage == iteration) Color.White else Color.White.copy(alpha = 0.5f)
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
