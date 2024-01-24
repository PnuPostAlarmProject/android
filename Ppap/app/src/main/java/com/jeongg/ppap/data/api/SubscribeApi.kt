package com.jeongg.ppap.data.api

import com.jeongg.ppap.data.dto.SubscribeCreateRequestDTO
import com.jeongg.ppap.data.dto.SubscribeUpdateRequestDTO
import com.jeongg.ppap.data.util.HttpRoutes
import com.jeongg.ppap.domain.repository.SubscribeRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class SubscribeApi(
    private val client: HttpClient
): SubscribeRepository {

    override suspend fun createSubscribe(requestDTO: SubscribeCreateRequestDTO): HttpResponse {
        return client.post(HttpRoutes.CREATE_SUBSCRIBE){
            setBody(requestDTO)
        }
    }

    override suspend fun getSubscribes(): HttpResponse{
        return client.get(HttpRoutes.GET_SUBSCRIBES)
    }

    override suspend fun getSubscribeById(subscribeId: Long): HttpResponse {
        return client.get(HttpRoutes.GET_SUBSCRIBE + "/$subscribeId")
    }

    override suspend fun updateSubscribe(subscribeId: Long, requestDTO: SubscribeUpdateRequestDTO): HttpResponse{
        return client.post(HttpRoutes.UPDATE_SUBSCRIBE + "/$subscribeId"){
            setBody(requestDTO)
        }
    }

    override suspend fun deleteSubscribe(subscribeId: Long): HttpResponse{
        return client.post(HttpRoutes.DELETE_SUBSCRIBE + "/$subscribeId"){}
    }

    override suspend fun updateActive(subscribeId: Long): HttpResponse{
        return client.post(HttpRoutes.UPDATE_ACTIVE + "/$subscribeId"){}
    }
}