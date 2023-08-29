package com.jeongg.ppap.di

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jeongg.ppap.data.user.UserDataSource
import com.jeongg.ppap.data.user.UserRepository
import com.jeongg.ppap.data.user.UserService
import com.jeongg.ppap.data.user.dto.KakaoLoginDTO
import com.jeongg.ppap.data.user.dto.RefreshTokenDTO
import com.jeongg.ppap.data.util.ACCESS_TOKEN_KEY
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.HttpRoutes
import com.jeongg.ppap.data.util.PDataStore
import com.jeongg.ppap.data.util.REFRESH_TOKEN_KEY
import com.jeongg.ppap.dataStore
import com.jeongg.ppap.util.log
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.accept
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(
        @ApplicationContext context: Context
    ): HttpClient {
        val accessToken = PDataStore(context).getData(ACCESS_TOKEN_KEY)
        val refreshToken = PDataStore(context).getData(REFRESH_TOKEN_KEY)
        "access token : $accessToken \n refresh token: $refreshToken".log()
        return HttpClient(CIO) {
            install(Logging){
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(Json{
                    prettyPrint = true
                    isLenient = true
                    encodeDefaults = true
                })
            }
            install(HttpTimeout) {
                connectTimeoutMillis = 5000
                requestTimeoutMillis = 5000
                socketTimeoutMillis = 5000
            }
            install(Auth){
                bearer {
                    refreshTokens {
                        val token = client.post(HttpRoutes.KAKAO_REISSUE){
                            setBody(RefreshTokenDTO(refreshToken))
                        }.body<ApiUtils.ApiResult<KakaoLoginDTO>>()
                        BearerTokens(
                            accessToken = token.response?.accessToken ?: "",
                            refreshToken = token.response?.refreshToken ?: ""
                        )
                    }
                }
            }
            defaultRequest{
                contentType(ContentType.Application.Json)
                accept(ContentType.Application.Json)
                if (accessToken.isNotEmpty()) headers.append(HttpHeaders.Authorization, accessToken)
            }
        }
    }
    @Provides
    @Singleton
    fun provideUserService(client: HttpClient): UserService {
        return UserDataSource(client)
    }
    @Provides
    @Singleton
    fun provideUserRepository(service: UserService): UserRepository {
        return UserRepository(service)
    }

}
