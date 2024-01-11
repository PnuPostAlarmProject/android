package com.jeongg.ppap.domain.usecase.scrap

import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.getErrorMessage
import com.jeongg.ppap.domain.repository.ScrapRepository
import com.jeongg.ppap.util.Resource
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class AddScrap @Inject constructor(
    private val scrapRepository: ScrapRepository
){
    operator fun invoke(contentId: Long): Flow<Resource<String>> = flow {
        try {
            emit(Resource.Loading())
            val response = scrapRepository.addScrap(contentId)
            val body = response.body<ApiUtils.ApiResult<String>>()
            val successMessage = body.response ?: "성공적으로 스크랩 완료했습니다."
            val errorMessage = body.error?.message ?: "스크랩에 실패하였습니다."

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