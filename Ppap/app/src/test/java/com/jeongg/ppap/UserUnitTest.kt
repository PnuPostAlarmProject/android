package com.jeongg.ppap

import com.jeongg.ppap.data.user.UserDataSource
import com.jeongg.ppap.util.getExceptionHttpClient
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

}