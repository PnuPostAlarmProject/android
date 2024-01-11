package com.jeongg.ppap.data.repository

import com.jeongg.ppap.data.api.ScrapApi
import com.jeongg.ppap.domain.repository.ScrapRepository
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScrapRepositoryImpl @Inject constructor(
    private val scrapApi: ScrapApi
): ScrapRepository{

    override suspend fun getScrapList(subscribeId: Long?, page: Int?): HttpResponse {
        return scrapApi.getScrapList(subscribeId, page)
    }

    override suspend fun addScrap(contentId: Long): HttpResponse {
        return scrapApi.addScrap(contentId)
    }

    override suspend fun deleteScrap(contentId: Long): HttpResponse {
        return scrapApi.deleteScrap(contentId)
    }
}