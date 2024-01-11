package com.jeongg.ppap.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenDTO(
    val refreshToken: String
)
