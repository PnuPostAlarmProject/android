package com.jeongg.ppap.data.subscribe.dto

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeWithContentDTO(
    val subscribeId: Long,
    val title: String,
    val noticeLink: String?,
    val rssLink: String,
    val isActive: Boolean
)
