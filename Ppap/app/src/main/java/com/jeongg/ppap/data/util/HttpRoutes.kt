package com.jeongg.ppap.data.util

object HttpRoutes {
    const val BASE_URL = "http://15.164.232.86"
    // user
    const val KAKAO_LOGIN = "/auth/kakao/login"
    const val KAKAO_REISSUE = "/auth/reissue"
    const val LOGOUT = "/auth/logout"
    const val WITHDRAWL = "/auth/withdrawal"
    // subscribe
    const val CREATE_SUBSCRIBE = "/subscribe"
    const val GET_SUBSCRIBES = "/subscribe"
    const val GET_SUBSCRIBE = "/subscribe"
    const val UPDATE_SUBSCRIBE = "/subscribe"
    const val DELETE_SUBSCRIBE = "/subscribe/delete"
    const val UPDATE_ACTIVE = "/subscribe/active"
    const val GET_UNIV_LIST = "/univ/list"
    const val GET_UNIV_SUBSCRIBE_LIST = "/univ"
    // notice
    const val GET_NOTICES = "/content"
    const val ADD_SCRAP = "/scrap/create"
    const val DELETE_SCRAP = "/scrap/delete/content"
    // scrap
    const val GET_SCRAPS = "/scrap"
    // page
    const val STARTING_PAGE_INDEX = 0
    const val PER_PAGE_SIZE = 10
}