package com.jeongg.ppap.data.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ScrapDTO(
    val contentId: Long,
    val contentTitle: String,
    val link: String,
    val pubDate: LocalDateTime,
    var isScrap: Boolean
)
