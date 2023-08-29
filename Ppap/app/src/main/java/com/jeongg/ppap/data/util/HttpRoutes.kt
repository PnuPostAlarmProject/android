package com.jeongg.ppap.data.util

object HttpRoutes {
    private const val BASE_URL = "http://192.168.219.100:8080"
    const val KAKAO_LOGIN = "$BASE_URL/auth/kakao/login"
    const val KAKAO_REISSUE = "$BASE_URL/auth/reissue"
    const val LOGOUT = "$BASE_URL/auth/logout"
    const val WITHDRAWL = "$BASE_URL/auth/withdrawl"
}