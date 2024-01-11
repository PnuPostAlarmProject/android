package com.jeongg.ppap.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class SubscribeDTO(
    val subscribeId: Long,
    val title: String
)