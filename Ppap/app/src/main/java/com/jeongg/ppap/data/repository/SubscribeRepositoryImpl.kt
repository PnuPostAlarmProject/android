package com.jeongg.ppap.data.repository

import com.jeongg.ppap.data.api.SubscribeApi
import com.jeongg.ppap.data.dto.SubscribeCreateRequestDTO
import com.jeongg.ppap.data.dto.SubscribeUpdateRequestDTO
import com.jeongg.ppap.domain.repository.SubscribeRepository
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubscribeRepositoryImpl @Inject constructor(
    private val subscribeApi: SubscribeApi
): SubscribeRepository{

    override suspend fun createSubscribe(requestDTO: SubscribeCreateRequestDTO): HttpResponse {
        return subscribeApi.createSubscribe(requestDTO)
    }

    override suspend fun getSubscribes(): HttpResponse{
        return subscribeApi.getSubscribes()
    }

    override suspend fun getSubscribeById(subscribeId: Long): HttpResponse{
        return subscribeApi.getSubscribeById(subscribeId)
    }

    override suspend fun updateSubscribe(subscribeId: Long, requestDTO: SubscribeUpdateRequestDTO): HttpResponse{
        return subscribeApi.updateSubscribe(subscribeId, requestDTO)
    }

    override suspend fun deleteSubscribe(subscribeId: Long): HttpResponse{
        return subscribeApi.deleteSubscribe(subscribeId)
    }

    override suspend fun updateActive(subscribeId: Long): HttpResponse{
        return subscribeApi.updateActive(subscribeId)
    }

    override suspend fun getUnivList(): HttpResponse{
        return subscribeApi.getUnivList()
    }

    override suspend fun getUnivSubscribeList(univId: Long): HttpResponse{
        return subscribeApi.getUnivSubscribeList(univId)
    }
}