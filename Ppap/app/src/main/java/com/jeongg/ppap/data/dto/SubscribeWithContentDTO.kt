package com.jeongg.ppap.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeWithContentDTO(
    val subscribeId: Long = 0,
    val title: String = "",
    val noticeLink: String? = null,
    val rssLink: String = "",
    val isActive: Boolean = false
)
