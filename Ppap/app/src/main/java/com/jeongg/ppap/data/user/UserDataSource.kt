package com.jeongg.ppap.data.user

import com.jeongg.ppap.data.user.dto.KakaoLoginDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.HttpRoutes
import com.jeongg.ppap.util.log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.header
import io.ktor.client.request.post

class UserDataSource(
    private val client: HttpClient
): UserService {

    override suspend fun kakaoLogin(accessToken: String): ApiUtils.ApiResult<KakaoLoginDTO> {
        return try {
             "accesstoken in source: $accessToken".log()
             return client.post(HttpRoutes.KAKAO_LOGIN){
                header("Kakao", accessToken)
             }.body()
        } catch(e: Exception){
            e.printStackTrace()
            "fail in userDataSource:  ${e.message}".log()
            ApiUtils.ApiResult(true, null, null)
        }
    }
}