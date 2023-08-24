package com.jeongg.ppap.data.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginDTO(
    val accessToken: String,
    val refreshToken: String
)
