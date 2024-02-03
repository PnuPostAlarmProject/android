package com.jeongg.ppap.di

import android.content.Context
import android.util.Log
import com.jeongg.ppap.data._const.DataStoreKey
import com.jeongg.ppap.data._const.HttpRoutes
import com.jeongg.ppap.data.api.NoticeApi
import com.jeongg.ppap.data.api.ScrapApi
import com.jeongg.ppap.data.api.SubscribeApi
import com.jeongg.ppap.data.api.UserApi
import com.jeongg.ppap.data.dto.KakaoLoginDTO
import com.jeongg.ppap.data.dto.RefreshTokenDTO
import com.jeongg.ppap.data.repository.NoticeRepositoryImpl
import com.jeongg.ppap.data.repository.ScrapRepositoryImpl
import com.jeongg.ppap.data.repository.SubscribeRepositoryImpl
import com.jeongg.ppap.data.repository.UserRepositoryImpl
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.PDataStore
import com.jeongg.ppap.domain.repository.NoticeRepository
import com.jeongg.ppap.domain.repository.ScrapRepository
import com.jeongg.ppap.domain.repository.SubscribeRepository
import com.jeongg.ppap.domain.repository.UserRepository
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
import io.ktor.client.plugins.auth.providers.RefreshTokensParams
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
                connectTimeoutMillis = 8000
                requestTimeoutMillis = 8000
                socketTimeoutMillis = 8000
            }
            install(Auth){
                bearer {
                    refreshTokens {
                        getRefreshToken(context)
                    }
                }
            }
            install(ContentEncoding) {
                deflate(1.0F)
                gzip(0.9F)
            }
            defaultRequest{
                contentType(ContentType.Application.Json)
                url(HttpRoutes.BASE_URL.path)

                val accessToken = PDataStore(context).getData(DataStoreKey.ACCESS_TOKEN_KEY.name)
                if (accessToken.isNotEmpty())
                    headers.appendIfNameAbsent(HttpHeaders.Authorization, accessToken)
            }
        }
    }

    private suspend fun RefreshTokensParams.getRefreshToken(context: Context): BearerTokens {
        val accessKey = DataStoreKey.ACCESS_TOKEN_KEY.name
        val refreshKey = DataStoreKey.REFRESH_TOKEN_KEY.name
        val refreshToken = PDataStore(context).getData(refreshKey)

        val apiResult = client.post(HttpRoutes.KAKAO_REISSUE.path) {
            setBody(RefreshTokenDTO(refreshToken))
            markAsRefreshTokenRequest()
        }.body<ApiUtils.ApiResult<KakaoLoginDTO>>()

        val newAccessToken = apiResult.response?.accessToken ?: ""
        val newRefreshToken = apiResult.response?.refreshToken ?: ""

        if (apiResult.success) {
            PDataStore(context).setData(accessKey, newAccessToken)
            PDataStore(context).setData(refreshKey, newRefreshToken)
        }
        return BearerTokens(
            accessToken = newAccessToken,
            refreshToken = newRefreshToken
        )
    }

    @Provides
    @Singleton
    fun provideUserApi(client: HttpClient): UserRepository {
        return UserApi(client)
    }
    @Provides
    @Singleton
    fun provideUserRepositoryImpl(userApi: UserApi): UserRepositoryImpl {
        return UserRepositoryImpl(userApi)
    }
    @Provides
    @Singleton
    fun provideSubscribeApi(client: HttpClient): SubscribeRepository {
        return SubscribeApi(client)
    }
    @Provides
    @Singleton
    fun provideSubscribeRepositoryImpl(subscribeApi: SubscribeApi): SubscribeRepositoryImpl {
        return SubscribeRepositoryImpl(subscribeApi)
    }
    @Provides
    @Singleton
    fun provideNoticeApi(client: HttpClient): NoticeRepository {
        return NoticeApi(client)
    }
    @Provides
    @Singleton
    fun provideNoticeRepositoryImpl(noticeApi: NoticeApi): NoticeRepositoryImpl {
        return NoticeRepositoryImpl(noticeApi)
    }
    @Provides
    @Singleton
    fun provideScrapApi(client: HttpClient): ScrapRepository {
        return ScrapApi(client)
    }
    @Provides
    @Singleton
    fun provideScrapRepositoryImpl(scrapApi: ScrapApi): ScrapRepositoryImpl {
        return ScrapRepositoryImpl(scrapApi)
    }
}
