package com.jeongg.ppap.data.scrap.dto

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ScrapDTO(
    val contentId: Long,
    val contentTitle: String,
    val link: String,
    val pubDate: LocalDateTime,
    val isScrap: Boolean
)
