package com.jeongg.ppap.data.subscribe

import com.jeongg.ppap.data.subscribe.dto.SubscribeCreateRequestDTO
import com.jeongg.ppap.data.subscribe.dto.SubscribeGetResponseDTO
import com.jeongg.ppap.data.subscribe.dto.SubscribeUpdateRequestDTO
import com.jeongg.ppap.data.subscribe.dto.SubscribeUpdateResponseDTO
import com.jeongg.ppap.data.subscribe.dto.SubscribeWithContentDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.getErrorMessage
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SubscribeRepository @Inject constructor(
    private val subscribeService: SubscribeService
){
    suspend fun createSubscribe(subscribeCreateRequestDTO: SubscribeCreateRequestDTO): ApiUtils.ApiResult<String> {
        return try {
            subscribeService.createSubscribe(subscribeCreateRequestDTO)
        } catch(e: Exception){
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }

    suspend fun getSubscribes(): ApiUtils.ApiResult<List<SubscribeGetResponseDTO>>{
        return try {
            subscribeService.getSubscribes()
        } catch(e: Exception){
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }

    suspend fun getSubscribeById(subscribeId: Long): ApiUtils.ApiResult<SubscribeWithContentDTO>{
        return try {
            subscribeService.getSubscribeById(subscribeId)
        } catch(e: Exception){
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }

    suspend fun updateSubscribe(subscribeId: Long, subscribeUpdateRequestDTO: SubscribeUpdateRequestDTO): ApiUtils.ApiResult<SubscribeUpdateResponseDTO>{
        return try {
            subscribeService.updateSubscribe(subscribeId, subscribeUpdateRequestDTO)
        } catch(e: Exception){
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }

    suspend fun deleteSubscribe(subscribeId: Long): ApiUtils.ApiResult<String>{
        return try {
            subscribeService.deleteSubscribe(subscribeId)
        } catch(e: Exception){
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }

    suspend fun updateActive(subscribeId: Long): ApiUtils.ApiResult<SubscribeUpdateResponseDTO>{
        return try {
            subscribeService.updateActive(subscribeId)
        } catch(e: Exception){
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }
}