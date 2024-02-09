package com.jeongg.ppap.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class KakaoLoginDTO(
    val accessToken: String,
    val refreshToken: String
)
