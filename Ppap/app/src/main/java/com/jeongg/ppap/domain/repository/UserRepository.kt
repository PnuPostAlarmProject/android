package com.jeongg.ppap.domain.repository

import io.ktor.client.statement.HttpResponse

interface UserRepository {
    suspend fun kakaoLogin(kakaoToken: String, fcmToken: String): HttpResponse
    suspend fun logout(): HttpResponse
    suspend fun withdraw(): HttpResponse
}