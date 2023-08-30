package com.jeongg.ppap.data.subscribe.dto

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeGetResponseDTO(
    val subscribeId: Long,
    val title: String,
    val isActive: Boolean
)
