package com.jeongg.ppap.presentation.scrap

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.component.PEmptyContent
import com.jeongg.ppap.presentation.component.PTabLayer
import com.jeongg.ppap.presentation.notice.NoticeItem
import com.jeongg.ppap.presentation.theme.Dimens
import com.jeongg.ppap.presentation.theme.gray3
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.log
import kotlinx.coroutines.flow.collectLatest

@Composable
fun ScrapScreen(
    navController: NavController,
    onUpPress: () -> Unit = {},
    viewModel: ScrapViewModel = hiltViewModel()
){
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val contents = viewModel.contents.collectAsLazyPagingItems()
    val context = LocalContext.current
    LaunchedEffect(key1=true){
        viewModel.getScrapList(null)
        viewModel.getScrapPage(null)
        viewModel.eventFlow.collectLatest { event ->
            when (event){
                is PEvent.SUCCESS -> { "스크랩 리스트 조회 성공".log() }
                is PEvent.ADD, PEvent.DELETE -> {}
                is PEvent.ERROR -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                else -> { "스크랩 리스트 로딩중".log() }
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Box(
            modifier = Modifier.padding(Dimens.ScreenPadding)
        ){
            Image(
                painter = painterResource(R.drawable.arrow),
                contentDescription = "navigate to back",
                modifier = Modifier
                    .align(Alignment.CenterStart)
                    .padding(end = 10.dp)
                    .size(30.dp)
                    .rotate(180f)
                    .clickable(onClick = onUpPress),
                colorFilter = ColorFilter.tint(gray3)
            )
            Text(
                text = stringResource(R.string.notice_scrap_title),
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
        if (viewModel.isEmptyList()) {
            PEmptyContent(message = "스크랩된 게시글이 없습니다.")
        }
        else {
            PTabLayer(
                tabs = viewModel.subscribes.value,
                selectedTabIndex = selectedTabIndex,
            ) { tabIndex ->
                selectedTabIndex = tabIndex
                viewModel.getScrapPage(viewModel.subscribes.value[selectedTabIndex].subscribeId)
            }
            PDivider()
            LazyColumn {
                items(
                    count = contents.itemCount,
                    key = contents.itemKey { it.contentId }
                ){index ->
                    val items = contents[index]
                    NoticeItem(
                        title = items?.contentTitle ?: "",
                        date = items?.pubDate?.date.toString(),
                        isBookmarked = items?.isScrap?: false,
                        link = items?.link ?: "",
                        onScrap = {viewModel.scrapEvent(it, items?.contentId ?: 0) }
                    )
                }
            }
        }
    }
}