package com.jeongg.ppap

import com.jeongg.ppap.data.api.ScrapApi
import com.jeongg.ppap.data.dto.scrap.ScrapListDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.util.getExceptionHttpClient
import io.ktor.client.call.body
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class ScrapUnitTest {
    @Test
    fun getScrapList_success(){
        runBlocking {
            // given
            val subscribeId = 1L
            val page = 1

            // when
            val mock = getExceptionHttpClient(SCRAP_LIST_CONTENT)
            val data = ScrapApi(mock).getScrapList(subscribeId, page).body<ApiUtils.ApiResult<ScrapListDTO>>()

            // then
            Assert.assertEquals(true, data.success)
            Assert.assertEquals("컴퓨터 및 프로그래밍 입문(001분반, 조환규 교수님) 수업을 신청한 수강생은 꼭 읽어주세요.", data.response?.scraps?.get(0)?.contentTitle ?: "")
            Assert.assertEquals(1L, data.response?.curSubscribeId)
            Assert.assertEquals(361L, data.response?.scraps?.get(0)?.contentId)
            Assert.assertEquals(null, data.error)
        }
    }

    @Test
    fun getScrapList_fail(){
        runBlocking {
            // given
            val content = """{"success": false, "response": null, "error": {"message": "존재하지 않는 스크랩 내용입니다.", "status": 404}}"""
            val subscribeId = 1L
            val page = 1

            // when
            val mock = getExceptionHttpClient(content)
            val data = ScrapApi(mock).getScrapList(subscribeId, page).body<ApiUtils.ApiResult<ScrapListDTO>>()

            // then
            Assert.assertEquals(false, data.success)
            Assert.assertEquals(null, data.response)
            Assert.assertEquals(404, data.error?.status ?: 0)
        }
    }
    @Test
    fun addScrap_success(){
        runBlocking {
            // given
            val content = """{"success": true, "response": null, "error": null}"""
            val contentId = 1L

            // when
            val mock = getExceptionHttpClient(content)
            val data = ScrapApi(mock).addScrap(contentId).body<ApiUtils.ApiResult<String>>()

            // then
            Assert.assertEquals(true, data.success)
            Assert.assertEquals(null, data.response)
            Assert.assertEquals(null, data.error)
        }
    }
    @Test
    fun addScrap_fail(){
        runBlocking {
            // given
            val content = """{"success": false, "response": null, "error": {"message": "이미 존재하는 스크랩입니다.", "status": 400}}"""
            val contentId = 1L

            // when
            val mock = getExceptionHttpClient(content)
            val data = ScrapApi(mock).addScrap(contentId).body<ApiUtils.ApiResult<String>>()

            // then
            Assert.assertEquals(false, data.success)
            Assert.assertEquals(null, data.response)
            Assert.assertEquals(400, data.error?.status ?: 0)
        }
    }
    @Test
    fun deleteScrap_success(){
        runBlocking {
            // given
            val content = """{"success": true, "response": null, "error": null}"""
            val contentId = 1L

            // when
            val mock = getExceptionHttpClient(content)
            val data = ScrapApi(mock).deleteScrap(contentId).body<ApiUtils.ApiResult<String>>()

            // then
            Assert.assertEquals(true, data.success)
            Assert.assertEquals(null, data.response)
            Assert.assertEquals(null, data.error)
        }
    }
    @Test
    fun deleteScrap_fail(){
        runBlocking {
            // given
            val content = """{"success": false, "response": null, "error": {"message": "존재하지 않는 스크랩입니다.", "status": 404}}"""
            val contentId = 1L

            // when
            val mock = getExceptionHttpClient(content)
            val data = ScrapApi(mock).deleteScrap(contentId).body<ApiUtils.ApiResult<String>>()

            // then
            Assert.assertEquals(false, data.success)
            Assert.assertEquals(null, data.response)
            Assert.assertEquals(404, data.error?.status ?: 0)
        }
    }
    companion object {
        val SCRAP_LIST_CONTENT = """
            {
            "success": true,
            "response": {
                "curSubscribeId": 1,
                "scraps": [
                    {
                        "contentId": 361,
                        "contentTitle": "컴퓨터 및 프로그래밍 입문(001분반, 조환규 교수님) 수업을 신청한 수강생은 꼭 읽어주세요.",
                        "pubDate": "2022-03-02T17:26:39.089",
                        "link": "http://his.pusan.ac.kr/bbs/cse/2615/931071/artclView.do",
                        "isScrap": true
                    },
                    {
                        "contentId": 362,
                        "contentTitle": "컴퓨터알고리즘(059분반) 조환규 교수님 수업을 신청한 수강생은 꼭 읽어주세요.",
                        "pubDate": "2022-02-28T18:30:17.097",
                        "link": "http://his.pusan.ac.kr/bbs/cse/2615/930883/artclView.do",
                        "isScrap": true
                    },
                    {
                        "contentId": 363,
                        "contentTitle": "[등록기간 연장]2021 ICPC 예선 안내(~9/30까지 신청)",
                        "pubDate": "2021-09-07T18:10:12.523",
                        "link": "http://his.pusan.ac.kr/bbs/cse/2615/880989/artclView.do",
                        "isScrap": true
                    },
                    {
                        "contentId": 364,
                        "contentTitle": "자료구조(060분반) 수업을 신청한 수강생은 꼭 읽어주세요.",
                        "pubDate": "2021-08-27T17:13:37.099",
                        "link": "http://his.pusan.ac.kr/bbs/cse/2615/879352/artclView.do",
                        "isScrap": true
                    },
                    {
                        "contentId": 365,
                        "contentTitle": "2021.1학기 논리설계 수업관련 (카카오톡 오픈채팅 링크 공지!)",
                        "pubDate": "2021-03-01T22:42:08.803",
                        "link": "http://his.pusan.ac.kr/bbs/cse/2615/859613/artclView.do",
                        "isScrap": true
                    },
                    {
                        "contentId": 366,
                        "contentTitle": "조환규 교수님 컴퓨터및프로그래밍입문(001분반)  수업 관련하여 공지드립니다.",
                        "pubDate": "2021-02-22T10:33:35.092",
                        "link": "http://his.pusan.ac.kr/bbs/cse/2615/858996/artclView.do",
                        "isScrap": true
                    },
                    {
                        "contentId": 367,
                        "contentTitle": "컴퓨터알고리즘(059분반) 수업을 신청한 수강생은 꼭 읽어주세요.",
                        "pubDate": "2021-02-19T16:06:17.031",
                        "link": "http://his.pusan.ac.kr/bbs/cse/2615/858935/artclView.do",
                        "isScrap": true
                    },
                    {
                        "contentId": 368,
                        "contentTitle": "ACM-ICPC  대회 진행 안내",
                        "pubDate": "2020-10-06T15:30:15.683",
                        "link": "http://his.pusan.ac.kr/bbs/cse/2615/835761/artclView.do",
                        "isScrap": true
                    },
                    {
                        "contentId": 369,
                        "contentTitle": "프로그래밍 대회 ACM-ICPC 2020 안내",
                        "pubDate": "2020-09-10T16:54:25.913",
                        "link": "http://his.pusan.ac.kr/bbs/cse/2615/833201/artclView.do",
                        "isScrap": true
                    },
                    {
                        "contentId": 370,
                        "contentTitle": "자료구조(059분반) 수업을 신청한 학생은 꼭 읽어주세요.",
                        "pubDate": "2020-09-02T17:14:14",
                        "link": "http://his.pusan.ac.kr/bbs/cse/2615/832273/artclView.do",
                        "isScrap": true
                    }
                ]
            },
            "error": null
        }
        """.trimIndent()
    }
}