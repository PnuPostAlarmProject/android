package com.jeongg.ppap.data.api

import com.jeongg.ppap.data.util.HttpRoutes
import com.jeongg.ppap.domain.repository.ScrapRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.statement.HttpResponse

class ScrapApi(
    private val client: HttpClient
): ScrapRepository {

    override suspend fun getScrapList(subscribeId: Long?, page: Int?): HttpResponse {
        val path = if (subscribeId == null) "" else "/$subscribeId"
        return client.get(HttpRoutes.GET_SCRAPS + path){
            parameter("page", page)
        }
    }

    override suspend fun addScrap(contentId: Long): HttpResponse {
        return client.post(HttpRoutes.ADD_SCRAP + "/$contentId"){}
    }

    override suspend fun deleteScrap(contentId: Long): HttpResponse {
        return client.post(HttpRoutes.DELETE_SCRAP + "/$contentId"){}
    }

}