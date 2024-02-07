package com.jeongg.ppap.domain.usecase.user

import com.jeongg.ppap.data._const.DataStoreKey
import com.jeongg.ppap.data.dto.KakaoLoginDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.PDataStore
import com.jeongg.ppap.data.util.getErrorMessage
import com.jeongg.ppap.domain.repository.UserRepository
import com.jeongg.ppap.util.Resource
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class KakaoLogin @Inject constructor(
    private val userRepository: UserRepository,
    private val dataStore: PDataStore
){
    operator fun invoke(kakaoToken: String, fcmTokenDTO: String): Flow<Resource<KakaoLoginDTO>> = flow {
        try {
            emit(Resource.Loading())
            val response = userRepository.kakaoLogin(kakaoToken, fcmTokenDTO)
            val body = response.body<ApiUtils.ApiResult<KakaoLoginDTO>>()
            val success = body.response ?: KakaoLoginDTO("", "")
            val errorMessage = body.error?.message ?: "카카오 로그인에 실패하였습니다."

            if (response.status == HttpStatusCode.OK && body.success) {
                dataStore.setData(DataStoreKey.ACCESS_TOKEN_KEY.name, success.accessToken)
                dataStore.setData(DataStoreKey.REFRESH_TOKEN_KEY.name, success.refreshToken)
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