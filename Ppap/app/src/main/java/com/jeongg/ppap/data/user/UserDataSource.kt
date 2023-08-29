package com.jeongg.ppap.data.user

import com.jeongg.ppap.data.user.dto.FcmTokenDTO
import com.jeongg.ppap.data.user.dto.KakaoLoginDTO
import com.jeongg.ppap.data.user.dto.RefreshTokenDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.HttpRoutes
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody

class UserDataSource(
    private val client: HttpClient
): UserService {

    override suspend fun kakaoLogin(kakaoToken: String, fcmToken: String): ApiUtils.ApiResult<KakaoLoginDTO> {
        return client.post(HttpRoutes.KAKAO_LOGIN){
            header("Kakao", kakaoToken)
            setBody(FcmTokenDTO(fcmToken))
        }.body()
    }

    override suspend fun reissue(refreshToken: String): ApiUtils.ApiResult<KakaoLoginDTO> {
        return client.post(HttpRoutes.KAKAO_REISSUE){
            setBody(RefreshTokenDTO(refreshToken))
        }.body()
    }

    override suspend fun logout(): ApiUtils.ApiResult<KakaoLoginDTO> {
        return client.post(HttpRoutes.LOGOUT){}.body()
    }

    override suspend fun withdraw(): ApiUtils.ApiResult<String> {
        return client.post(HttpRoutes.WITHDRAWL){}.body()
    }
}