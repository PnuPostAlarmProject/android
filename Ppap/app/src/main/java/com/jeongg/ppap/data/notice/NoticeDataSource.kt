package com.jeongg.ppap.data.notice

import com.jeongg.ppap.data.notice.dto.SubscribeWithContentScrapDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.HttpRoutes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post

class NoticeDataSource(
    private val client: HttpClient
): NoticeService {

    override suspend fun getNoticeList(
        subscribeId: Long?,
        page: Int?
    ): ApiUtils.ApiResult<SubscribeWithContentScrapDTO> {
        val path = if (subscribeId == null) "" else "/$subscribeId"
        return client.get(HttpRoutes.GET_NOTICES + path){
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