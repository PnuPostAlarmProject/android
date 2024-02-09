package com.jeongg.ppap.data.dto.notice

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class NoticeDTO (
    val contentId: Long,
    val title: String,
    val pubDate: LocalDateTime,
    val link: String,
    var isScraped: Boolean
)
