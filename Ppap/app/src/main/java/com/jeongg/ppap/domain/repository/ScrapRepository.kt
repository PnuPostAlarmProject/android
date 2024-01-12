package com.jeongg.ppap.domain.repository

import io.ktor.client.statement.HttpResponse

interface ScrapRepository {
    suspend fun getScrapList(subscribeId: Long?, page: Int?): HttpResponse
    suspend fun addScrap(contentId: Long): HttpResponse
    suspend fun deleteScrap(contentId: Long):HttpResponse
}