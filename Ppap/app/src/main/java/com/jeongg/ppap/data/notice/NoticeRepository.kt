package com.jeongg.ppap.data.notice

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jeongg.ppap.data.notice.dto.NoticeDTO
import com.jeongg.ppap.data.notice.dto.NoticeWithSubscribeDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.HttpRoutes.PER_PAGE_SIZE
import com.jeongg.ppap.data.util.getErrorMessage
import com.jeongg.ppap.util.log
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoticeRepository @Inject constructor(
    private val noticeService: NoticeService
) {
    fun getNoticePage(subscribeId: Long?): Flow<PagingData<NoticeDTO>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PER_PAGE_SIZE),
            pagingSourceFactory = { NoticePagingSource(noticeService, subscribeId) }
        ).flow
    }

    suspend fun getNoticeList(subscribeId: Long?, page: Int?): ApiUtils.ApiResult<NoticeWithSubscribeDTO> {
        return try {
            noticeService.getNoticeList(subscribeId, page)
        } catch(e: Exception) {
            "getNoticeList-fail: ${e.message}".log()
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }
}