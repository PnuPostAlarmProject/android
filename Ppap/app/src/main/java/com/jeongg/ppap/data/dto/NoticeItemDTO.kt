package com.jeongg.ppap.data.dto

import androidx.compose.runtime.MutableState

data class NoticeItemDTO(
    val contentId: Long = 0,
    val title: String = "",
    val date: String = "",
    val link: String = "",
    val isScraped: Boolean = false,
    val onScrapClick: (MutableState<Boolean>) -> Unit = {}
)