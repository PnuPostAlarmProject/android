package com.jeongg.ppap.presentation.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.R
import com.jeongg.ppap.data.dto.notice.NoticeItemDTO
import com.jeongg.ppap.presentation.component.util.clickAsSingle
import com.jeongg.ppap.presentation.component.util.noRippleClickable
import com.jeongg.ppap.theme.gray3

@Composable
fun NoticeItem(
    noticeItemDTO: NoticeItemDTO
){
    val urlHandler = LocalUriHandler.current
    val isScraped = rememberSaveable { mutableStateOf(noticeItemDTO.isScraped) }
    Column(
        modifier = Modifier
            .noRippleClickable { urlHandler.openUri(noticeItemDTO.link) }
            .padding(horizontal = 20.dp)
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 13.dp)
        ) {
            NoticeContent(noticeItemDTO.title, noticeItemDTO.date)
            NoticeBookmark(
                modifier = Modifier.align(Alignment.CenterEnd),
                isBookmarked = isScraped.value,
                onClick = { noticeItemDTO.onScrapClick(isScraped) }
            )
        }
        PDivider()
    }
}

@Composable
private fun NoticeContent(title: String, date: String) {
    Column(
        modifier = Modifier.padding(end = 33.dp),
        verticalArrangement = Arrangement.spacedBy(5.dp),
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.bodyLarge,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        Text(
            text = date,
            style = MaterialTheme.typography.labelMedium,
            color = gray3
        )
    }
}

@Composable
private fun NoticeBookmark(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    isBookmarked: Boolean,
){
    val favorite = if (isBookmarked) R.drawable.heart_filled
                   else R.drawable.heart_empty
    Image(
        painter = painterResource(favorite),
        contentDescription = if (isBookmarked) "스크랩 삭제하기" else "스크랩 추가하기",
        modifier = modifier
            .size(25.dp)
            .clickAsSingle(onClick = onClick)
    )
}