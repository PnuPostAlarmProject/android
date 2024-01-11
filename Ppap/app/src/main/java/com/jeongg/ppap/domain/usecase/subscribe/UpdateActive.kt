package com.jeongg.ppap.domain.usecase.subscribe

import com.jeongg.ppap.data.dto.SubscribeUpdateResponseDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.getErrorMessage
import com.jeongg.ppap.domain.repository.SubscribeRepository
import com.jeongg.ppap.util.Resource
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateActive @Inject constructor(
    private val subscribeRepository: SubscribeRepository
){
    operator fun invoke(subscribeId: Long): Flow<Resource<SubscribeUpdateResponseDTO>> = flow {
        try {
            emit(Resource.Loading())
            val response = subscribeRepository.updateActive(subscribeId)
            val body = response.body<ApiUtils.ApiResult<SubscribeUpdateResponseDTO>>()
            val success = body.response ?: SubscribeUpdateResponseDTO()
            val errorMessage = body.error?.message ?: "구독 비활성화/활성화에 실패하였습니다."

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