package com.jeongg.ppap.domain.repository

import io.ktor.client.statement.HttpResponse

interface NoticeRepository {
    suspend fun getNoticeList(subscribeId: Long?, page: Int?): HttpResponse
}