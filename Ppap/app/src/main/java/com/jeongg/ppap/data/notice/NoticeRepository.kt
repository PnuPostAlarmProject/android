package com.jeongg.ppap.data.notice

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jeongg.ppap.data.notice.dto.ContentScrapDTO
import com.jeongg.ppap.data.notice.dto.SubscribeWithContentScrapDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.getErrorMessage
import com.jeongg.ppap.util.log
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoticeRepository @Inject constructor(
    private val noticeService: NoticeService
) {
    fun getNoticePage(subscribeId: Long?): Flow<PagingData<ContentScrapDTO>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PAGE_SIZE),
            pagingSourceFactory = { NoticePagingSource(noticeService, subscribeId) }
        ).flow
    }

    suspend fun getNoticeList(subscribeId: Long?, page: Int?): ApiUtils.ApiResult<SubscribeWithContentScrapDTO> {
        return try {
            noticeService.getNoticeList(subscribeId, page)
        } catch(e: Exception) {
            "getNoticeList-fail: ${e.message}".log()
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }

    suspend fun addScrap(contentId: Long): ApiUtils.ApiResult<String> {
        return try {
            noticeService.addScrap(contentId)
        } catch(e: Exception) {
            "addScrap-fail: ${e.message}".log()
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }

    suspend fun deleteScrap(contentId: Long): ApiUtils.ApiResult<String> {
        return try {
            noticeService.deleteScrap(contentId)
        } catch(e: Exception) {
            "deleteScrap-fail: ${e.message}".log()
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }
    companion object {
        private const val PAGE_SIZE = 10
    }
}