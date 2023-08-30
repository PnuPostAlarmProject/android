package com.jeongg.ppap.data.subscribe

import com.jeongg.ppap.data.subscribe.dto.SubscribeCreateRequestDTO
import com.jeongg.ppap.data.subscribe.dto.SubscribeGetResponseDTO
import com.jeongg.ppap.data.subscribe.dto.SubscribeUpdateRequestDTO
import com.jeongg.ppap.data.subscribe.dto.SubscribeUpdateResponseDTO
import com.jeongg.ppap.data.subscribe.dto.SubscribeWithContentDTO
import com.jeongg.ppap.data.util.ApiUtils

interface SubscribeService {
    suspend fun createSubscribe(subscribeCreateRequestDTO: SubscribeCreateRequestDTO): ApiUtils.ApiResult<String>
    suspend fun getSubscribes(): ApiUtils.ApiResult<List<SubscribeGetResponseDTO>>
    suspend fun getSubscribeById(subscribeId: Long): ApiUtils.ApiResult<SubscribeWithContentDTO>
    suspend fun updateSubscribe(subscribeId: Long, subscribeUpdateRequestDTO: SubscribeUpdateRequestDTO): ApiUtils.ApiResult<SubscribeUpdateResponseDTO>
    suspend fun deleteSubscribe(subscribeId: Long): ApiUtils.ApiResult<String>
    suspend fun updateActive(subscribeId: Long): ApiUtils.ApiResult<SubscribeUpdateResponseDTO>
}