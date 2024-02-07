package com.jeongg.ppap.data.api

import com.jeongg.ppap.domain.repository.NoticeRepository
import com.jeongg.ppap.data._const.HttpRoutes
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.HttpResponse

class NoticeApi(
    private val client: HttpClient
): NoticeRepository {

    override suspend fun getNoticeList(subscribeId: Long?, page: Int?): HttpResponse {
        val subscribePath = if (subscribeId == null) "" else "/$subscribeId"
        return client.get(HttpRoutes.GET_NOTICES.path + subscribePath){
            parameter("page", page)
        }
    }
}