package com.jeongg.ppap.di

import com.jeongg.ppap.data.user.UserDataSource
import com.jeongg.ppap.data.user.UserService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(CIO) {
            install(Logging)
            install(ContentNegotiation) {
                json(Json{
                    prettyPrint = true
                    isLenient = true
                })
            }
        }
    }
    @Provides
    @Singleton
    fun provideUserService(client: HttpClient): UserService {
        return UserDataSource(client)
    }

}
