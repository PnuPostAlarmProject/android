package com.jeongg.ppap.data.dto.subscribe

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeCreateRequestDTO(
    val title: String,
    val rssLink: String,
    val noticeLink: String?
)
