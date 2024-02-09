package com.jeongg.ppap.data.dto.subscribe

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeUpdateResponseDTO(
    val subscribeId: Long = 0,
    val title: String = "",
    val noticeLink: String? = null,
    val isActive: Boolean = false
)
