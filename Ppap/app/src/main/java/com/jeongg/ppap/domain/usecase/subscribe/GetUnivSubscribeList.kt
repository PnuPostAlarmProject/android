package com.jeongg.ppap.domain.usecase.subscribe

import com.jeongg.ppap.data.dto.univ.UnivNoticeBoardDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.getErrorMessage
import com.jeongg.ppap.domain.repository.SubscribeRepository
import com.jeongg.ppap.util.Resource
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetUnivSubscribeList @Inject constructor(
    private val subscribeRepository: SubscribeRepository
){
    operator fun invoke(univId: Long): Flow<Resource<List<UnivNoticeBoardDTO>>> = flow {
        try {
            emit(Resource.Loading())
            val response = subscribeRepository.getUnivSubscribeList(univId)
            val body = response.body<ApiUtils.ApiResult<List<UnivNoticeBoardDTO>>>()
            val success = body.response ?: emptyList()
            val errorMessage = body.error?.message ?: "구독할 게시판 리스트 조회에 실패하였습니다."

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