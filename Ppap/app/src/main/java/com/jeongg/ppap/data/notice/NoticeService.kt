package com.jeongg.ppap.data.notice

import androidx.paging.PagingData
import com.jeongg.ppap.data.notice.dto.ContentScrapDTO
import com.jeongg.ppap.data.notice.dto.SubscribeWithContentScrapDTO
import com.jeongg.ppap.data.util.ApiUtils
import kotlinx.coroutines.flow.Flow

interface NoticeService {
    suspend fun getNoticeList(subscribeId: Long?, page: Int?): ApiUtils.ApiResult<SubscribeWithContentScrapDTO>
    suspend fun addScrap(contentId: Long): ApiUtils.ApiResult<String>
    suspend fun deleteScrap(contentId: Long): ApiUtils.ApiResult<String>
}