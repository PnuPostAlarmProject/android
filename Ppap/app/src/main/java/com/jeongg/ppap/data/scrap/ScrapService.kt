package com.jeongg.ppap.data.scrap

import com.jeongg.ppap.data.scrap.dto.ScrapWithSubscribeDTO
import com.jeongg.ppap.data.util.ApiUtils

interface ScrapService {
    suspend fun getScrapList(subscribeId: Long?, page: Int?): ApiUtils.ApiResult<ScrapWithSubscribeDTO>
    suspend fun addScrap(contentId: Long): ApiUtils.ApiResult<String>
    suspend fun deleteScrap(contentId: Long): ApiUtils.ApiResult<String>
}