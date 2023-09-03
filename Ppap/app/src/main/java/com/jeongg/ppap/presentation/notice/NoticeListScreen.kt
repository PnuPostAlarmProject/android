package com.jeongg.ppap.presentation.notice

import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.component.PEmptyContent
import com.jeongg.ppap.presentation.component.PTabLayer
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.presentation.theme.Dimens
import com.jeongg.ppap.presentation.theme.bright_pink
import com.jeongg.ppap.presentation.theme.gray3
import com.jeongg.ppap.presentation.theme.main_green
import com.jeongg.ppap.presentation.theme.very_bright_yellow
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.log
import kotlinx.coroutines.flow.collectLatest


@Composable
fun NoticeListScreen(
    navController: NavController,
    viewModel: NoticeViewModel = hiltViewModel()
){
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val contents = viewModel.contents.collectAsLazyPagingItems()
    val context = LocalContext.current
    LaunchedEffect(key1 = true){
        viewModel.eventFlow.collectLatest { event ->
            when(event){
                is PEvent.EMPTY -> {}
                is PEvent.SUCCESS -> {}
                is PEvent.ERROR -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                else -> { "공지 리스트 로딩중".log() }
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        NoticeListTitle(
            bookmarkNavigate = {navController.navigate(Screen.ScrapScreen.route)},
            settingNavigate = {navController.navigate(Screen.SettingScreen.route)}
        )
        if (viewModel.isEmptyList()) {
            NoticeListBanner(navController)
            PEmptyContent(message = "구독한 공지사항이 없어요.")
        }
        else {
            PTabLayer(
                tabs = viewModel.subscribes.value,
                selectedTabIndex = selectedTabIndex
            ) { tabIndex ->
                selectedTabIndex = tabIndex
                // TODO: 가능하다면 쿼리를 한 번으로 고치고 싶은데...
                viewModel.getNoticePage(viewModel.subscribes.value[selectedTabIndex].subscribeId)
                viewModel.getNoticeList(viewModel.subscribes.value[selectedTabIndex].subscribeId)
            }
            PDivider()
            LazyColumn {
                item { NoticeListBanner(navController) }
                items(
                    count = contents.itemCount,
                    key = contents.itemKey { it.contentId }
                ){index ->
                    val items = contents[index]
                    NoticeItem(
                        title = items?.title ?: "",
                        date = items?.pubDate?.date.toString(),
                        isBookmarked = items?.isScraped?: false,
                        link = items?.link ?: "",
                        onScrap = {viewModel.scrapEvent(it, items?.contentId ?: 0)}
                    )
                }
            }
        }
    }
}
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoticeListBanner(
    navController: NavController
) {
    val state = rememberPagerState(initialPage = 0) { 3 }
    val colors = listOf(very_bright_yellow, bright_pink, main_green)
    val images = listOf(R.drawable.pineapple, R.drawable.apple_no_background, R.drawable.pineapple)
    val titles = listOf(R.string.banner_title3, R.string.banner_title2, R.string.banner_title1)
    val descriptions = listOf(R.string.banner_description3, R.string.banner_description2, R.string.banner_description1)
    val screens = listOf(Screen.SubscribeScreen.route, Screen.SubscribeAddScreen.route, Screen.NoticeScrapScreen.route)

    HorizontalPager(
        state = state,
        pageSpacing = 5.dp,
        contentPadding = PaddingValues(30.dp, 20.dp, 30.dp, 10.dp),
    ) { index ->
        val imageSize by animateFloatAsState(
            targetValue =  if (state.currentPage == index) 1f else 0.9f,
            animationSpec = tween(durationMillis = 200), label = ""
        )

        Column(
            modifier = Modifier
                .graphicsLayer {
                    scaleX = imageSize
                    scaleY = imageSize
                }
                .clip(MaterialTheme.shapes.small)
                .clickable { navController.navigate(screens[index]) }
                .fillMaxWidth()
                .defaultMinSize(minHeight = 62.dp)
                .background(colors[index])
                .padding(10.dp, 10.dp, 10.dp, 0.dp),
            verticalArrangement = Arrangement.Center
        ){
            Row(
                verticalAlignment = CenterVertically
            ){
                Image(
                    painter = painterResource(images[index]),
                    contentDescription = "character",
                    modifier = Modifier
                        .padding(end = 15.dp)
                        .height(44.dp)
                )
                Column {
                    Text(
                        text = stringResource(titles[index]),
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.Black
                    )
                    Text(
                        text = stringResource(descriptions[index]),
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Black
                    )
                }
            }
            // horizontal indicator
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                repeat(3) { iteration ->
                    val color = Color.White.copy(alpha = if(state.currentPage == iteration) 1f else 0.5f)
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

@Composable
fun NoticeListTitle(
    bookmarkNavigate: () -> Unit = {},
    settingNavigate: () -> Unit = {}
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(Dimens.ScreenPadding)
    ) {
        Text(
            text = stringResource(R.string.app_name),
            style = MaterialTheme.typography.titleLarge
        )
        Row(
            modifier = Modifier.align(Alignment.CenterEnd),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(R.drawable.bookmark),
                contentDescription = "bookmark",
                modifier = Modifier.clickable(onClick = bookmarkNavigate)
            )
            Icon(
                painter = painterResource(R.drawable.setting),
                contentDescription = "setting",
                modifier = Modifier.clickable(onClick = settingNavigate),
                tint = gray3
            )
        }
    }
}