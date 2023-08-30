package com.jeongg.ppap.data.subscribe.dto

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeUpdateResponseDTO(
    val subscribeId: Long,
    val title: String,
    val noticeLink: String?,
    val isActive: Boolean
)
