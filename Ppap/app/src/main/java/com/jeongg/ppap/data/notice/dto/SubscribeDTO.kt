package com.jeongg.ppap.data.notice.dto

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeDTO(
    val subscribeId: Long,
    val title: String
)