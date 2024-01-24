package com.jeongg.ppap.data.dto

data class NoticeItemDTO(
    val contentId: Long = 0,
    val title: String = "",
    val date: String = "",
    val link: String = "",
    val isScraped: Boolean = false,
    val onScrapClick: () -> Unit = {}
)