package com.jeongg.ppap.presentation.noticeItem

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.jeongg.ppap.R
import com.jeongg.ppap.data.dto.NoticeItemDTO
import com.jeongg.ppap.presentation.component.PDivider
import com.jeongg.ppap.presentation.component.noRippleClickable
import com.jeongg.ppap.theme.gray3

@Composable
fun NoticeItem(
    isBookmarked: MutableState<Boolean> = mutableStateOf(false),
    noticeItemDTO: NoticeItemDTO
){
    val urlHandler = LocalUriHandler.current
    Column(
        modifier = Modifier
            .noRippleClickable{ urlHandler.openUri(noticeItemDTO.link) }
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
                isBookmarked = isBookmarked,
                onClick = {
                    noticeItemDTO.onScrapClick()
                    isBookmarked.value = isBookmarked.value.not()
                }
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
    isBookmarked: MutableState<Boolean> = mutableStateOf(false),
){
    val favorite = if (isBookmarked.value) R.drawable.heart_filled
                   else R.drawable.heart_empty
    Image(
        painter = painterResource(favorite),
        contentDescription = "checked: ${isBookmarked.value}",
        modifier = modifier.size(25.dp).noRippleClickable(onClick = onClick)
    )
}