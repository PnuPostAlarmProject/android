package com.jeongg.ppap.data.user.dto

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenDTO(
    val refreshToken: String
)
