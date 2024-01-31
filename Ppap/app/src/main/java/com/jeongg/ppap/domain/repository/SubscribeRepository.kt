package com.jeongg.ppap.domain.repository

import com.jeongg.ppap.data.dto.SubscribeCreateRequestDTO
import com.jeongg.ppap.data.dto.SubscribeUpdateRequestDTO
import io.ktor.client.statement.HttpResponse

interface SubscribeRepository {
    suspend fun createSubscribe(requestDTO: SubscribeCreateRequestDTO): HttpResponse
    suspend fun getSubscribes(): HttpResponse
    suspend fun getSubscribeById(subscribeId: Long): HttpResponse
    suspend fun updateSubscribe(subscribeId: Long, requestDTO: SubscribeUpdateRequestDTO): HttpResponse
    suspend fun deleteSubscribe(subscribeId: Long):HttpResponse
    suspend fun updateActive(subscribeId: Long): HttpResponse
    suspend fun getUnivList(): HttpResponse
    suspend fun getUnivSubscribeList(univId: Long): HttpResponse
}