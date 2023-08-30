package com.jeongg.ppap.data.subscribe.dto

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeUpdateRequestDTO(
    val title: String,
    val noticeLink: String?,
)
