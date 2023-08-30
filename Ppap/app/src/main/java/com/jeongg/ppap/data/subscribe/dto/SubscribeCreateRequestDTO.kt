package com.jeongg.ppap.data.subscribe.dto

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeCreateRequestDTO(
    val title: String,
    val rssLink: String,
    val noticeLink: String?
)
