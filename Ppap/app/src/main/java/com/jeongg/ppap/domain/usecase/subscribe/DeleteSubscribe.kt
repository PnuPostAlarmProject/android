package com.jeongg.ppap.domain.usecase.subscribe

import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.getErrorMessage
import com.jeongg.ppap.domain.repository.SubscribeRepository
import com.jeongg.ppap.util.Resource
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DeleteSubscribe @Inject constructor(
    private val subscribeRepository: SubscribeRepository
){
    operator fun invoke(subscribeId: Long): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = subscribeRepository.deleteSubscribe(subscribeId)
            val body = response.body<ApiUtils.ApiResult<String>>()
            val successMessage = body.response ?: "성공적으로 구독을 삭제했습니다."
            val errorMessage = body.error?.message ?: "구독 삭제에 실패하였습니다."

            if (response.status == HttpStatusCode.OK && body.success) {
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