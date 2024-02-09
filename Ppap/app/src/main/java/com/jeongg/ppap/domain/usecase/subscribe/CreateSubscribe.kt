package com.jeongg.ppap.domain.usecase.subscribe

import com.jeongg.ppap.data.dto.subscribe.SubscribeCreateRequestDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.getErrorMessage
import com.jeongg.ppap.domain.repository.SubscribeRepository
import com.jeongg.ppap.util.Resource
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CreateSubscribe @Inject constructor(
    private val subscribeRepository: SubscribeRepository
){
    operator fun invoke(requestDTO: SubscribeCreateRequestDTO): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = subscribeRepository.createSubscribe(requestDTO)
            val body = response.body<ApiUtils.ApiResult<String>>()
            val successMessage = body.response ?: "성공적으로 구독을 추가했습니다."
            val errorMessage = body.error?.message ?: "구독 추가에 실패하였습니다."

            if (response.status == HttpStatusCode.Created && body.success) {
                emit(Resource.Success(successMessage))
            }
            else {
                emit(Resource.Error(errorMessage))
            }
        } catch(error: Exception){
            emit(Resource.Error(getErrorMessage(error)))
        }
    }
}