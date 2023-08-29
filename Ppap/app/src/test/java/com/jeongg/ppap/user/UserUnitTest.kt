package com.jeongg.ppap.user

import com.jeongg.ppap.data.user.UserDataSource
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
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class UserUnitTest {

    @Test
    fun login_success(){
        runBlocking {
            //given
            val content = """{"success": true, "response": {"accessToken": "AT", "refreshToken": "RT"}, "error": null}"""

            // when
            val mock = getExceptionHttpClient(content)
            val data = UserDataSource(mock).kakaoLogin("test-token", "test-token")

            // then
            Assert.assertEquals(true, data.success)
            Assert.assertEquals("AT", data.response?.accessToken ?: "")
            Assert.assertEquals("RT", data.response?.refreshToken ?: "")
            Assert.assertEquals(null, data.error)
        }
    }
    @Test
    fun login_fail_kakao_api_linking(){
        runBlocking {
            //given
            val content = """{"success": false, "response": null, "error": {"message": "카카오 API 연동 중 문제가 발생했습니다", "status": 500}}"""

            // when
            val mock = getExceptionHttpClient(content)
            val data = UserDataSource(mock).kakaoLogin("test-token", "test-token")

            // then
            Assert.assertEquals(false, data.success)
            Assert.assertEquals("카카오 API 연동 중 문제가 발생했습니다", data.error?.message ?: "")
            Assert.assertEquals(500, data.error?.status ?: -1)
        }
    }
    @Test
    fun reissue_success(){
        runBlocking {
            //given
            val content = """{"success": true, "response": {"accessToken": "AT", "refreshToken": "RT"}, "error": null}"""

            // when
            val mock = getExceptionHttpClient(content)
            val data = UserDataSource(mock).reissue("test-refresh-token")

            // then
            Assert.assertEquals(true, data.success)
            Assert.assertEquals("AT", data.response?.accessToken ?: "")
            Assert.assertEquals("RT", data.response?.refreshToken ?: "")
            Assert.assertEquals(null, data.error)
        }
    }
    @Test
    fun reissue_fail_invalid_test(){
        runBlocking {
            //given
            val content = """{"success": false, "response": null, "error": {"message": "유효하지 않은 토큰입니다.", "status": 400}}"""

            // when
            val mock = getExceptionHttpClient(content)
            val data = UserDataSource(mock).kakaoLogin("test-token", "test-token")

            // then
            Assert.assertEquals(false, data.success)
            Assert.assertEquals("유효하지 않은 토큰입니다.", data.error?.message ?: "")
            Assert.assertEquals(400, data.error?.status ?: -1)
        }
    }
    @Test
    fun logout_success(){
        runBlocking {
            //given
            val content = """{"success": true, "response": null, "error": null}"""

            // when
            val mock = getExceptionHttpClient(content)
            val data = UserDataSource(mock).logout()

            // then
            Assert.assertEquals(true, data.success)
            Assert.assertEquals(null, data.response)
            Assert.assertEquals(null, data.error)
        }
    }
    @Test
    fun logout_fail_invalid_test(){
        runBlocking {
            //given
            val content = """{"success": false, "response": null, "error": {"message": "유효하지 않은 토큰입니다.", "status": 400}}"""

            // when
            val mock = getExceptionHttpClient(content)
            val data = UserDataSource(mock).logout()

            // then
            Assert.assertEquals(false, data.success)
            Assert.assertEquals("유효하지 않은 토큰입니다.", data.error?.message ?: "")
            Assert.assertEquals(400, data.error?.status ?: -1)
        }
    }
    private fun getExceptionHttpClient(content: String): HttpClient{
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
}