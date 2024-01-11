package com.jeongg.ppap.domain.usecase.subscribe

import com.jeongg.ppap.data.dto.SubscribeGetResponseDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.getErrorMessage
import com.jeongg.ppap.domain.repository.SubscribeRepository
import com.jeongg.ppap.util.Resource
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetSubscribes @Inject constructor(
    private val subscribeRepository: SubscribeRepository
){
    operator fun invoke(): Flow<Resource<List<SubscribeGetResponseDTO>>> = flow {
        try {
            emit(Resource.Loading())
            val response = subscribeRepository.getSubscribes()
            val body = response.body<ApiUtils.ApiResult<List<SubscribeGetResponseDTO>>>()
            val success = body.response ?: emptyList()
            val errorMessage = body.error?.message ?: "구독 리스트 조회에 실패하였습니다."

            if (response.status == HttpStatusCode.OK && body.success) {
                emit(Resource.Success(success))
            }
            else {
                emit(Resource.Error(errorMessage))
            }
        } catch(error: Exception){
            emit(Resource.Error(getErrorMessage(error)))
        }
    }
}