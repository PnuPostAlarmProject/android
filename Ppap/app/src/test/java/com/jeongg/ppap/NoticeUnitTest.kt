package com.jeongg.ppap

import com.jeongg.ppap.data.notice.NoticeDataSource
import com.jeongg.ppap.util.getExceptionHttpClient
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class NoticeUnitTest {

    @Test
    fun getNoticeList_success(){
        runBlocking {
            // given
            val subscribeId = 1L
            val page = 1

            // when
            val mock = getExceptionHttpClient(NOTICE_LIST_CONTENT)
            val data = NoticeDataSource(mock).getNoticeList(subscribeId, page)

            // then
            Assert.assertEquals(true, data.success)
            Assert.assertEquals("테스트1", data.response?.subscribes?.get(0)?.title ?: "")
            Assert.assertEquals(3, data.response?.subscribes?.size ?: 0)
            Assert.assertEquals(1L, data.response?.curSubscribeId)
            Assert.assertEquals(361L, data.response?.contents?.get(0)?.contentId)
            Assert.assertEquals(null, data.error)
        }
    }

    @Test
    fun getNoticeList_fail(){
        runBlocking {
            // given
            val content = """{"success": false, "response": null, "error": {"message": "존재하지 않는 공지사항 내용입니다.", "status": 404}}"""
            val subscribeId = 1L
            val page = 1

            // when
            val mock = getExceptionHttpClient(content)
            val data = NoticeDataSource(mock).getNoticeList(subscribeId, page)

            // then
            Assert.assertEquals(false, data.success)
            Assert.assertEquals(null, data.response)
            Assert.assertEquals(404, data.error?.status ?: 0)
        }
    }

    companion object {
        val NOTICE_LIST_CONTENT = """
            {
                "success": true,
                "response": {
                    "subscribes": [
                        {
                            "subscribeId": 1,
                            "title": "테스트1"
                        },
                        {
                            "subscribeId": 2,
                            "title": "테스트2"
                        },
                        {
                            "subscribeId": 3,
                            "title": "테스트3"
                        }
                    ],
                    "curSubscribeId": 1,
                    "contents": [
                        {
                            "contentId": 361,
                            "title": "컴퓨터 및 프로그래밍 입문(001분반, 조환규 교수님) 수업을 신청한 수강생은 꼭 읽어주세요.",
                            "pubDate": "2022-03-02T17:26:39.089",
                            "link": "http://his.pusan.ac.kr/bbs/cse/2615/931071/artclView.do",
                            "isScraped": true
                        },
                        {
                            "contentId": 362,
                            "title": "컴퓨터알고리즘(059분반) 조환규 교수님 수업을 신청한 수강생은 꼭 읽어주세요.",
                            "pubDate": "2022-02-28T18:30:17.097",
                            "link": "http://his.pusan.ac.kr/bbs/cse/2615/930883/artclView.do",
                            "isScraped": false
                        }
                    ]
                },
                "error": null
            }
        """.trimIndent()
    }
}