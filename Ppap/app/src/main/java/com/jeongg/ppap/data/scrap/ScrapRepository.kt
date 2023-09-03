package com.jeongg.ppap.data.scrap

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jeongg.ppap.data.scrap.dto.ScrapDTO
import com.jeongg.ppap.data.scrap.dto.ScrapWithSubscribeDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.HttpRoutes.PER_PAGE_SIZE
import com.jeongg.ppap.data.util.getErrorMessage
import com.jeongg.ppap.util.log
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScrapRepository @Inject constructor(
    private val scrapService: ScrapService
){
    fun getScrapPage(subscribeId: Long?): Flow<PagingData<ScrapDTO>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = PER_PAGE_SIZE),
            pagingSourceFactory = { ScrapPagingSource(scrapService, subscribeId) }
        ).flow
    }
    suspend fun getScrapList(subscribeId: Long?, page: Int?): ApiUtils.ApiResult<ScrapWithSubscribeDTO> {
        return try {
            scrapService.getScrapList(subscribeId, page)
        } catch(e: Exception) {
            "getNoticeList-fail: ${e.message}".log()
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }
    suspend fun addScrap(contentId: Long): ApiUtils.ApiResult<String> {
        return try {
            scrapService.addScrap(contentId)
        } catch(e: Exception) {
            "addScrap-fail: ${e.message}".log()
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }

    suspend fun deleteScrap(contentId: Long): ApiUtils.ApiResult<String> {
        return try {
            scrapService.deleteScrap(contentId)
        } catch(e: Exception) {
            "deleteScrap-fail: ${e.message}".log()
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }
}