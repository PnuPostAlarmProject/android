package com.jeongg.ppap.data._const

enum class HttpRoutes(
    val path: String
) {
    BASE_HOST("api.pnunotification.site"),
    BASE_PATH("api/v0/"),

    // user
    KAKAO_LOGIN("auth/kakao/login"),
    KAKAO_REISSUE("auth/reissue"),
    LOGOUT("auth/logout"),
    WITHDRAWL("auth/withdrawal"),

    // subscribe
    CREATE_SUBSCRIBE("subscribe"),
    GET_SUBSCRIBES("subscribe"),
    GET_SUBSCRIBE("subscribe"),
    UPDATE_SUBSCRIBE("subscribe"),
    DELETE_SUBSCRIBE("subscribe/delete"),
    UPDATE_ACTIVE("subscribe/active"),
    GET_UNIV_LIST("univ/list"),
    GET_UNIV_SUBSCRIBE_LIST("univ"),

    // notice
    GET_NOTICES("content"),
    ADD_SCRAP("scrap/create"),
    DELETE_SCRAP("scrap/delete/content"),

    // scrap
    GET_SCRAPS("scrap"),
}