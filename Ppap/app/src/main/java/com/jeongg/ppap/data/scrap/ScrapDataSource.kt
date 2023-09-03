package com.jeongg.ppap.data.scrap

import com.jeongg.ppap.data.scrap.dto.ScrapWithSubscribeDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.HttpRoutes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post

class ScrapDataSource(
    private val client: HttpClient
): ScrapService {
    override suspend fun getScrapList(
        subscribeId: Long?,
        page: Int?
    ): ApiUtils.ApiResult<ScrapWithSubscribeDTO> {
        val path = if (subscribeId == null) "" else "/$subscribeId"
        return client.get(HttpRoutes.GET_SCRAPS + path){
            parameter("page", page)
        }.body()
    }

    override suspend fun addScrap(contentId: Long): ApiUtils.ApiResult<String> {
        return client.post(HttpRoutes.ADD_SCRAP + "/$contentId"){}.body()
    }

    override suspend fun deleteScrap(contentId: Long): ApiUtils.ApiResult<String> {
        return client.post(HttpRoutes.DELETE_SCRAP + "/$contentId"){}.body()
    }

}