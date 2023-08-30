package com.jeongg.ppap

import com.jeongg.ppap.data.subscribe.SubscribeDataSource
import com.jeongg.ppap.data.subscribe.dto.SubscribeCreateRequestDTO
import com.jeongg.ppap.data.subscribe.dto.SubscribeUpdateRequestDTO
import com.jeongg.ppap.util.getExceptionHttpClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class SubscribeUnitTest {

    @Test
    fun createSubscribe_success(){
        runBlocking {
            // given
            val content = """{"success": true, "response": created, "error": null}"""
            val subscribe = SubscribeCreateRequestDTO("title", "rssLink", "noticeLink")

            // when
            val mock = getExceptionHttpClient(content)
            val data = SubscribeDataSource(mock).createSubscribe(subscribe)

            // then
            Assert.assertEquals(true, data.success)
            Assert.assertEquals("created", data.response ?: "")
            Assert.assertEquals(null, data.error)
        }
    }
    @Test
    fun createSubscribe_fail(){
        runBlocking {
            // given
            val content = """{"success": false, "response": null, "error": {"message": "유효하지 않은 RSS 링크입니다.", "status": 400}}"""
            val subscribe = SubscribeCreateRequestDTO("title", "rssLink", "noticeLink")

            // when
            val mock = getExceptionHttpClient(content)
            val data = SubscribeDataSource(mock).createSubscribe(subscribe)

            // then
            Assert.assertEquals(false, data.success)
            Assert.assertEquals(null, data.response)
            Assert.assertEquals(400, data.error?.status ?: 0)
        }
    }

    @Test
    fun getSubscribes_success(){
        runBlocking {
            // given
            val content = """{
                    "success": true,
                    "response": [
                        {
                            "subscribeId": 1,
                            "title": "정컴 학부 공지사항",
                            "isActive": true
                        },
                        {
                            "subscribeId": 2,
                            "title": "정컴 학부 공지사항",
                            "isActive": true
                        }
                    ],
                    "error": null
                }
                """.trimIndent()

            // when
            val mock = getExceptionHttpClient(content)
            val data = SubscribeDataSource(mock).getSubscribes()

            // then
            Assert.assertEquals(true, data.success)
            Assert.assertEquals(1, data.response?.get(0)?.subscribeId ?: 0)
            Assert.assertEquals(2, data.response?.size ?: 0)
            Assert.assertEquals(null, data.error)
        }
    }
    @Test
    fun getSubscribes_fail(){
        runBlocking {
            // given
            val content = """{"success": false, "response": null, "error": {"message": "빈 구독 목록입니다.", "status": 404}}"""

            // when
            val mock = getExceptionHttpClient(content)
            val data = SubscribeDataSource(mock).getSubscribes()

            // then
            Assert.assertEquals(false, data.success)
            Assert.assertEquals(null, data.response)
            Assert.assertEquals(404, data.error?.status ?: 0)
        }
    }

    @Test
    fun getSubscribeById_success(){
        runBlocking {
            // given
            val subscribeId = 1L
            val content = """
                {
                    "success": true,
                    "response": {
                        "subscribeId": 1,
                        "title": "테스트1",
                        "noticeLink": null,
                        "rssLink": "https://cse.pusan.ac.kr/bbs/cse/2615/rssList.do",
                        "isActive": true
                    },
                    "error": null
                }
                """.trimIndent()

            // when
            val mock = getExceptionHttpClient(content)
            val data = SubscribeDataSource(mock).getSubscribeById(subscribeId)

            // then
            Assert.assertEquals(true, data.success)
            Assert.assertEquals(1, data.response?.subscribeId ?: 0)
            Assert.assertEquals("테스트1", data.response?.title ?: "")
            Assert.assertEquals(null, data.response?.noticeLink)
            Assert.assertEquals(null, data.error)
        }
    }
    @Test
    fun getSubscribeById_fail(){
        runBlocking {
            // given
            val subscribeId = 1L
            val content = """{"success": false, "response": null, "error": {"message": "빈 구독 목록입니다.", "status": 404}}"""

            // when
            val mock = getExceptionHttpClient(content)
            val data = SubscribeDataSource(mock).getSubscribeById(subscribeId)

            // then
            Assert.assertEquals(false, data.success)
            Assert.assertEquals(null, data.response)
            Assert.assertEquals(404, data.error?.status ?: 0)
        }
    }
    @Test
    fun updateSubscribe_success(){
        runBlocking {
            // given
            val subscribeId = 1L
            val content = """
                {
                    "success": true,
                    "response": {
                        "subscribeId": 1,
                        "title": "정컴 공지",
                        "noticeLink": null,
                        "isActive": true
                    },
                    "error": null
                }
                """.trimIndent()

            // when
            val mock = getExceptionHttpClient(content)
            val data = SubscribeDataSource(mock).updateSubscribe(
                    subscribeId = subscribeId,
                    subscribeUpdateRequestDTO = SubscribeUpdateRequestDTO("정컴 공지",  null)
                )

            // then
            Assert.assertEquals(true, data.success)
            Assert.assertEquals(1, data.response?.subscribeId ?: 0)
            Assert.assertEquals("정컴 공지", data.response?.title ?: "")
            Assert.assertEquals(null, data.response?.noticeLink)
            Assert.assertEquals(true, data.response?.isActive)
            Assert.assertEquals(null, data.error)
        }
    }
    @Test
    fun updateSubscribe_fail(){
        runBlocking {
            // given
            val subscribeId = 1L
            val content = """{"success": false, "response": null, "error": {"message": "빈 구독 목록입니다.", "status": 400}}"""

            // when
            val mock = getExceptionHttpClient(content)
            val data = SubscribeDataSource(mock).updateSubscribe(
                subscribeId = subscribeId,
                subscribeUpdateRequestDTO = SubscribeUpdateRequestDTO("부산대 RSS 링크가 아닙니다.",  null)
            )

            // then
            Assert.assertEquals(false, data.success)
            Assert.assertEquals(null, data.response)
            Assert.assertEquals(400, data.error?.status ?: 0)
        }
    }
    @Test
    fun deleteSubscribe_success(){
        runBlocking {
            // given
            val subscribeId = 1L
            val content = """{"success": true, "response": null, "error": null}"""

            // when
            val mock = getExceptionHttpClient(content)
            val data = SubscribeDataSource(mock).deleteSubscribe(subscribeId = subscribeId)

            // then
            Assert.assertEquals(true, data.success)
            Assert.assertEquals(null, data.response)
            Assert.assertEquals(null, data.error)
        }
    }
    @Test
    fun deleteSubscribe_fail(){
        runBlocking {
            // given
            val subscribeId = 1L
            val content = """{"success": false, "response": null, "error": {"message": "허가되지 않은 접근입니다.", "status": 403}}"""

            // when
            val mock = getExceptionHttpClient(content)
            val data = SubscribeDataSource(mock).deleteSubscribe(subscribeId = subscribeId)

            // then
            Assert.assertEquals(false, data.success)
            Assert.assertEquals(null, data.response)
            Assert.assertEquals(403, data.error?.status ?: 0)
        }
    }
    @Test
    fun updateActive_success(){
        runBlocking {
            // given
            val subscribeId = 1L
            val content = """
            {
                "success": true,
                "response": {
                    "subscribeId": 1,
                    "title": "정컴 공지",
                    "noticeLink": null,
                    "isActive": false
                },
                "error": null
            }
            """.trimIndent()

            // when
            val mock = getExceptionHttpClient(content)
            val data = SubscribeDataSource(mock).updateActive(subscribeId = subscribeId)

            // then
            Assert.assertEquals(true, data.success)
            Assert.assertEquals(1, data.response?.subscribeId ?: 0)
            Assert.assertEquals("정컴 공지", data.response?.title ?: "")
            Assert.assertEquals(null, data.response?.noticeLink)
            Assert.assertEquals(null, data.error)
        }
    }
    @Test
    fun updateActive_fail(){
        runBlocking {
            // given
            val subscribeId = 1L
            val content = """{"success": false, "response": null, "error": {"message": "존재하지 않는 구독입니다.", "status": 404}}"""

            // when
            val mock = getExceptionHttpClient(content)
            val data = SubscribeDataSource(mock).updateActive(subscribeId = subscribeId)

            // then
            Assert.assertEquals(false, data.success)
            Assert.assertEquals(null, data.response)
            Assert.assertEquals(404, data.error?.status ?: 0)
        }
    }
}