package com.jeongg.ppap.presentation.notice

import androidx.compose.animation.AnimatedContentTransitionScope.SlideDirection.Companion.End
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontVariation.width
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.navigation.Screen
import com.jeongg.ppap.presentation.subscribe.ImageDescription
import com.jeongg.ppap.presentation.subscribe.TextDescription
import com.jeongg.ppap.ui.theme.Dimens
import com.jeongg.ppap.ui.theme.bright_pink
import com.jeongg.ppap.ui.theme.bright_yellow
import com.jeongg.ppap.ui.theme.main_green
import com.jeongg.ppap.ui.theme.main_yellow
import com.jeongg.ppap.ui.theme.shapes

@Composable
fun NoticeListScreen(
    navController: NavController
){
    Column(
        modifier = Modifier
            .padding(Dimens.PaddingNormal)
            .fillMaxSize()
    ){
        NoticeListTitle()
        PDivider(modifier = Modifier.padding(top = 7.dp, bottom = 15.dp))
        LazyColumn {
            item { NoticeListBanner() }
            repeat(15){
                item { NoticeItem() }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NoticeListBanner() {
    val state = rememberPagerState(initialPage = 0) { 3 }
    val colors = listOf(bright_yellow, bright_pink, main_green)
    val images = listOf(R.drawable.pineapple, R.drawable.apple_no_background, R.drawable.pineapple)
    val titles = listOf("스크랩 화면 바로가기", "공지 추가 등록하기", "구독한 공지사항 수정하기")
    val descriptions = listOf("하트를 눌러서 공지사항을 저장하세요", "학과 공지사항 등 필요한 공지를 등록해주세요.", "알림 여부, 공지 이름 등을 수정해보세요.")
    val screens = listOf(Screen.SubscribeScreen.route, Screen.SubscribeAddScreen.route, Screen.SubscribeScreen.route)

    HorizontalPager(
        state = state,
        pageSpacing = 15.dp
    ) { index ->
        Column(
            modifier = Modifier
                .clip(shapes.small)
                .clickable{ }
                .fillMaxWidth()
                .defaultMinSize(minHeight = 62.dp)
                .background(colors[index])
                .padding(10.dp, 10.dp, 10.dp, 0.dp),
            verticalArrangement = Arrangement.Center
        ){
            Row{
                Image(
                    painter = painterResource(images[index]),
                    contentDescription = "character",
                    modifier = Modifier.padding(end = 15.dp).height(44.dp)
                )
                Column {
                    Text(
                        text = titles[index],
                        style = MaterialTheme.typography.displayLarge,
                        color = Color.Black
                    )
                    Text(
                        text = descriptions[index],
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

@Composable
fun NoticeListTitle(){
    Box(
        modifier = Modifier.fillMaxWidth()
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
                modifier = Modifier.clickable { }
            )
            Icon(
                painter = painterResource(R.drawable.setting),
                contentDescription = "setting",
                modifier = Modifier.clickable { },
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}