package com.jeongg.ppap.data.util

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import org.apache.commons.lang3.SerializationException
import java.io.IOException
import java.util.concurrent.TimeoutException

fun getErrorMessage(e: Exception): Pair<String, Int>{
    return when (e){
        is RedirectResponseException -> Pair("리다렉션 에러가 발생했습니다.", 300)
        is ClientRequestException ->  Pair("요청한 내용이 올바르지 않습니다.", 400)
        is ServerResponseException -> Pair("서버에 문제가 생겼습니다.", 500)
        is TimeoutException -> Pair("네트워크 상황을 확인해주세요.", 600)
        is IOException -> Pair("서버 연결 오류가 발생했습니다.", 700)
        is SerializationException -> Pair("데이터 처리 중 오류가 발생했습니다.", 800)
        else -> Pair("예상치 못한 에러입니다.", -1)
    }
}