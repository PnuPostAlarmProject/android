package com.jeongg.ppap.util

import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import io.ktor.utils.io.ByteReadChannel

fun getExceptionHttpClient(content: String): HttpClient {
    return HttpClient(
        MockEngine {
            respond(
                content = ByteReadChannel(content),
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
    ){
        install(ContentNegotiation) {
            json()
        }
        install(Logging){
            level = LogLevel.ALL
        }
        defaultRequest {
            contentType(ContentType.Application.Json)
        }
    }
}