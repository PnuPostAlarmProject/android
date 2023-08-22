package com.jeongg.ppap.di

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jeongg.ppap.data.user.UserDataSource
import com.jeongg.ppap.data.user.UserRepository
import com.jeongg.ppap.data.user.UserService
import com.jeongg.ppap.data.util.KAKAO_TOKEN_KEY
import com.jeongg.ppap.dataStore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
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
        val token = runBlocking(Dispatchers.IO){
            context.dataStore.data.map{ it[stringPreferencesKey(KAKAO_TOKEN_KEY)] ?: "null" }.first()
        }
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
                connectTimeoutMillis = 10000
                requestTimeoutMillis = 10000
                socketTimeoutMillis = 10000
            }
            defaultRequest{
                contentType(ContentType.Application.Json)
                // authorization - token
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
