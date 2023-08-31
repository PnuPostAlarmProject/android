package com.jeongg.ppap.presentation.scrap

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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.jeongg.ppap.R
import com.jeongg.ppap.data.notice.dto.SubscribeDTO
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.component.PEmptyContent
import com.jeongg.ppap.presentation.component.PTabLayer
import com.jeongg.ppap.presentation.notice.NoticeItem
import com.jeongg.ppap.presentation.theme.Dimens
import com.jeongg.ppap.presentation.theme.gray3

@Composable
fun NoticeScrapScreen(
    navController: NavController,
    onUpPress: () -> Unit = {}
){
    var selectedTabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf("학생지원시스템", "컴공 공지!", "전기 공지", "졸업게시판")
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
        PTabLayer(
            tabs = listOf(SubscribeDTO(1, "하잉")),
            selectedTabIndex = selectedTabIndex
        ) { tabIndex ->
            selectedTabIndex = tabIndex
        }
        PDivider()
        LazyColumn {
            repeat(10){ item { NoticeItem() } }
            item { PEmptyContent(modifier = Modifier.padding(vertical = 70.dp)) }
        }
    }
}