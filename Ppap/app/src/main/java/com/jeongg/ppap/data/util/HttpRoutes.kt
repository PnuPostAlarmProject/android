package com.jeongg.ppap.data.util

object HttpRoutes {
    const val BASE_URL = "http://192.168.219.100:8080"
    // user
    const val KAKAO_LOGIN = "/auth/kakao/login"
    const val KAKAO_REISSUE = "/auth/reissue"
    const val LOGOUT = "/auth/logout"
    const val WITHDRAWL = "/auth/withdrawl"
    // subscribe
    const val CREATE_SUBSCRIBE = "/subscribe"
    const val GET_SUBSCRIBES = "/subscribe"
    const val GET_SUBSCRIBE = "/subscribe"
    const val UPDATE_SUBSCRIBE = "/subscribe"
    const val DELETE_SUBSCRIBE = "/subscribe/delete"
    const val UPDATE_ACTIVE = "/subscribe/active"
    // notice
    const val GET_NOTICES = "/content"
    const val ADD_SCRAP = "/scrap/create"
    const val DELETE_SCRAP = "/scrap/delete/content"
}