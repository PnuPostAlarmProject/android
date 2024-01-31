package com.jeongg.ppap.presentation.noticeItem

import androidx.compose.foundation.lazy.LazyListScope
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
        NoticeItem(noticeItemDTO = contents[index] ?: NoticeItemDTO())
    }
}
