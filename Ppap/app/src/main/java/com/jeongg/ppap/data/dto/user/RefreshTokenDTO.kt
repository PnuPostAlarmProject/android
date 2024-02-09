package com.jeongg.ppap.data.dto.user

import kotlinx.serialization.Serializable

@Serializable
data class RefreshTokenDTO(
    val refreshToken: String
)
