package com.jeongg.ppap.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeGetResponseDTO(
    val subscribeId: Long,
    val title: String,
    val isActive: Boolean
)
