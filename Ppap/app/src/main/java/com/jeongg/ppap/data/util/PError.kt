package com.jeongg.ppap.data.util

import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import java.util.concurrent.TimeoutException

fun getErrorMessage(e: Exception): Pair<String, Int>{
    return when (e){
        is RedirectResponseException -> Pair("리다렉션 에러가 발생했습니다.", 300)
        is ClientRequestException ->  Pair("요청한 내용이 올바르지 않습니다.", 400)
        is ServerResponseException -> Pair("서버에 문제가 생겼습니다.", 500)
        is TimeoutException -> Pair("네트워크 상황을 확인해주세요.", 600)
        else -> Pair("예상치 못한 에러입니다.", -1)
    }
}