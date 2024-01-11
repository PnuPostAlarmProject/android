package com.jeongg.ppap.data.repository

import com.jeongg.ppap.data.api.UserApi
import com.jeongg.ppap.domain.repository.UserRepository
import io.ktor.client.statement.HttpResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
): UserRepository {

    override suspend fun kakaoLogin(kakaoToken: String, fcmToken: String): HttpResponse {
        return userApi.kakaoLogin(kakaoToken, fcmToken)
    }

    override suspend fun logout(): HttpResponse {
        return userApi.logout()
    }

    override suspend fun withdraw(): HttpResponse {
        return userApi.withdraw()
    }
}