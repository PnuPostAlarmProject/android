package com.jeongg.ppap.presentation.mapper

import androidx.compose.runtime.MutableState
import androidx.paging.PagingData
import androidx.paging.map
import com.jeongg.ppap.data.dto.notice.NoticeDTO
import com.jeongg.ppap.data.dto.notice.NoticeItemDTO
import com.jeongg.ppap.data.dto.scrap.ScrapDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class NoticeItemMapper {

    fun noticeToNoticeItem(
        noticePagingData: Flow<PagingData<NoticeDTO>>,
        scrapEvent: (MutableState<Boolean>, NoticeDTO) -> Unit,
    ): Flow<PagingData<NoticeItemDTO>> {
        return noticePagingData.map { noticeDTO ->
            noticeDTO.map { notice ->
                NoticeItemDTO(
                    contentId = notice.contentId,
                    title = notice.title,
                    date = notice.pubDate.date.toString(),
                    link = notice.link,
                    isScraped = notice.isScraped,
                    onScrapClick = { scrapEvent(it, notice) }
                )
            }
        }
    }

    fun scrapToNoticeItem(
        scrapPagingData: Flow<PagingData<ScrapDTO>>,
        scrapEvent: (MutableState<Boolean>, ScrapDTO) -> Unit,
    ): Flow<PagingData<NoticeItemDTO>> {
        return scrapPagingData.map { scrapDTO ->
            scrapDTO.map { notice ->
                NoticeItemDTO(
                    contentId = notice.contentId,
                    title = notice.contentTitle,
                    date = notice.pubDate.date.toString(),
                    link = notice.link,
                    isScraped = notice.isScrap,
                    onScrapClick = { scrapEvent(it, notice) }
                )
            }
        }
    }
}