package com.jeongg.ppap.presentation.notice

import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemKey
import com.jeongg.ppap.data.dto.NoticeItemDTO

fun LazyListScope.noticeItemContent(
    contents: LazyPagingItems<NoticeItemDTO>
) {
    items(
        count = contents.itemCount,
        key = contents.itemKey { it.contentId }
    ) { index ->
        val items = contents[index]
        val isBookmarked = rememberSaveable { mutableStateOf(items?.isScraped ?: false) }
        NoticeItem(
            isBookmarked = isBookmarked,
            noticeItemDTO = items ?: NoticeItemDTO()
        )
    }
}
