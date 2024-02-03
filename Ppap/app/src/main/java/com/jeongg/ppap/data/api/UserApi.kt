package com.jeongg.ppap.data.api

import com.jeongg.ppap.data.dto.FcmTokenDTO
import com.jeongg.ppap.data._const.HttpRoutes
import com.jeongg.ppap.domain.repository.UserRepository
import io.ktor.client.HttpClient
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse

class UserApi(
    private val client: HttpClient
): UserRepository {

    override suspend fun kakaoLogin(kakaoToken: String, fcmToken: String): HttpResponse {
        return client.post(HttpRoutes.KAKAO_LOGIN.path){
            header("Kakao", kakaoToken)
            setBody(FcmTokenDTO(fcmToken))
        }
    }

    override suspend fun logout(): HttpResponse {
        return client.post(HttpRoutes.LOGOUT.path){}
    }

    override suspend fun withdraw(): HttpResponse {
        return client.post(HttpRoutes.WITHDRAWL.path){}
    }
}