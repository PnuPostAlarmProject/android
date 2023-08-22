package com.jeongg.ppap.domain.usecase.user

import com.jeongg.ppap.data.user.UserRepository
import com.jeongg.ppap.data.user.dto.KakaoLoginDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.PDataStore
import com.jeongg.ppap.util.Resource
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KakaoReissueUsecase @Inject constructor(
    private val repository: UserRepository,
) {
    operator fun invoke(token: String): Flow<Resource<ApiUtils.ApiResult<KakaoLoginDTO>>> = flow {
        try {
            emit(Resource.Loading())
            val data = repository.kakaoReissue(token)
            if(data.success) {
                emit(Resource.Success(data))
            }
            else if(data.error != null) emit(Resource.Error(data.error.message))

        } catch(e: RedirectResponseException) {
            // 3xx - responses
            emit(Resource.Error(e.response.status.description))
        } catch(e: ClientRequestException) {
            // 4xx - responses
            emit(Resource.Error(e.response.status.description))
        } catch(e: ServerResponseException) {
            // 5xx - responses
            emit(Resource.Error(e.response.status.description))
        } catch(e: Exception) {
            emit(Resource.Error(e.message.toString()))
        }
    }
}