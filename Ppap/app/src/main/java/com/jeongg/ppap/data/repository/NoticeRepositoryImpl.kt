package com.jeongg.ppap.data.repository

import com.jeongg.ppap.data.api.NoticeApi
import com.jeongg.ppap.domain.repository.NoticeRepository
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoticeRepositoryImpl @Inject constructor(
    private val noticeApi: NoticeApi
): NoticeRepository {

    override suspend fun getNoticeList(subscribeId: Long?, page: Int?): HttpResponse {
        return noticeApi.getNoticeList(subscribeId, page)
    }
}