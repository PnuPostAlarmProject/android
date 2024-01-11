package com.jeongg.ppap.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginDTO(
    val accessToken: String,
    val refreshToken: String
)
