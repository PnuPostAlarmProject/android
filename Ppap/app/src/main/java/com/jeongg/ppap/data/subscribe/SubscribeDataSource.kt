package com.jeongg.ppap.data.subscribe

import com.jeongg.ppap.data.subscribe.dto.SubscribeCreateRequestDTO
import com.jeongg.ppap.data.subscribe.dto.SubscribeGetResponseDTO
import com.jeongg.ppap.data.subscribe.dto.SubscribeUpdateRequestDTO
import com.jeongg.ppap.data.subscribe.dto.SubscribeUpdateResponseDTO
import com.jeongg.ppap.data.subscribe.dto.SubscribeWithContentDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.HttpRoutes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class SubscribeDataSource(
    private val client: HttpClient
): SubscribeService {

    override suspend fun createSubscribe(subscribeCreateRequestDTO: SubscribeCreateRequestDTO): ApiUtils.ApiResult<String>{
        return client.post(HttpRoutes.CREATE_SUBSCRIBE){
            setBody(subscribeCreateRequestDTO)
        }.body()
    }

    override suspend fun getSubscribes(): ApiUtils.ApiResult<List<SubscribeGetResponseDTO>>{
        return client.get(HttpRoutes.GET_SUBSCRIBES).body()
    }

    override suspend fun getSubscribeById(subscribeId: Long): ApiUtils.ApiResult<SubscribeWithContentDTO> {
        return client.get(HttpRoutes.GET_SUBSCRIBE + "/$subscribeId").body()
    }

    override suspend fun updateSubscribe(
        subscribeId: Long,
        subscribeUpdateRequestDTO: SubscribeUpdateRequestDTO
    ): ApiUtils.ApiResult<SubscribeUpdateResponseDTO>{
        return client.post(HttpRoutes.UPDATE_SUBSCRIBE + "/$subscribeId"){
            setBody(subscribeUpdateRequestDTO)
        }.body()
    }

    override suspend fun deleteSubscribe(subscribeId: Long): ApiUtils.ApiResult<String>{
        return client.post(HttpRoutes.DELETE_SUBSCRIBE + "/$subscribeId"){}.body()
    }

    override suspend fun updateActive(subscribeId: Long): ApiUtils.ApiResult<SubscribeUpdateResponseDTO>{
        return client.post(HttpRoutes.UPDATE_ACTIVE + "/$subscribeId"){}.body()
    }
}