package com.jeongg.ppap.presentation.util

data class PState(
    val isRefresh: Boolean = false,
    val isLoading: Boolean = false,
    val isEmpty: Boolean = false,
    val isSubscribeEmpty: Boolean = false,
    val errorMessage: String = ""
)