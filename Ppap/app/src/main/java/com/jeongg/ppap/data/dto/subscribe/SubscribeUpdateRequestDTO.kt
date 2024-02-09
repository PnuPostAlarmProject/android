package com.jeongg.ppap.data.dto.subscribe

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeUpdateRequestDTO(
    val title: String,
    val noticeLink: String?,
)
