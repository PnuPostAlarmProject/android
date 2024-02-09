package com.jeongg.ppap.data.dto.subscribe

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeGetResponseDTO(
    val subscribeId: Long,
    val title: String,
    var isActive: Boolean
)
