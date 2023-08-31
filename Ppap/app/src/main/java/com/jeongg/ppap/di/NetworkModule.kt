package com.jeongg.ppap.di

import android.content.Context
import android.util.Log
import com.jeongg.ppap.data.notice.NoticeDataSource
import com.jeongg.ppap.data.notice.NoticeRepository
import com.jeongg.ppap.data.notice.NoticeService
import com.jeongg.ppap.data.subscribe.SubscribeDataSource
import com.jeongg.ppap.data.subscribe.SubscribeRepository
import com.jeongg.ppap.data.subscribe.SubscribeService
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
import io.ktor.client.plugins.compression.ContentEncoding
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.appendIfNameAbsent
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
        return HttpClient(CIO) {
            install(Logging){
                logger = object: Logger {
                    override fun log(message: String){
                        Log.d("ppap_api", message)
                    }
                }
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
                        val refreshToken = PDataStore(context).getData(REFRESH_TOKEN_KEY)
                        val token = client.post(HttpRoutes.KAKAO_REISSUE){
                            setBody(RefreshTokenDTO(refreshToken))
                            markAsRefreshTokenRequest()
                        }.body<ApiUtils.ApiResult<KakaoLoginDTO>>()

                        if (token.success){
                            PDataStore(context).setData(ACCESS_TOKEN_KEY, token.response?.accessToken ?: "")
                            PDataStore(context).setData(REFRESH_TOKEN_KEY, token.response?.refreshToken ?: "")
                        }
                        BearerTokens(
                            accessToken = token.response?.accessToken ?: "",
                            refreshToken = token.response?.refreshToken ?: ""
                        )
                    }
                }
            }
            install(ContentEncoding) {
                deflate(1.0F)
                gzip(0.9F)
            }
            defaultRequest{
                val accessToken = PDataStore(context).getData(ACCESS_TOKEN_KEY)
                contentType(ContentType.Application.Json)
                if (accessToken.isNotEmpty())
                    headers.appendIfNameAbsent(HttpHeaders.Authorization, accessToken)
                url(HttpRoutes.BASE_URL)
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
    @Provides
    @Singleton
    fun provideSubscribeService(client: HttpClient): SubscribeService {
        return SubscribeDataSource(client)
    }
    @Provides
    @Singleton
    fun provideSubscribeRepository(service: SubscribeService): SubscribeRepository {
        return SubscribeRepository(service)
    }
    @Provides
    @Singleton
    fun provideNoticeService(client: HttpClient): NoticeService {
        return NoticeDataSource(client)
    }
    @Provides
    @Singleton
    fun provideNoticeRepository(service: NoticeService): NoticeRepository {
        return NoticeRepository(service)
    }

}
