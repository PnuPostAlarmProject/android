package com.jeongg.ppap.presentation.subscribe_add

sealed class SubscribeAddEvent{
    data class EnteredTitle(val title: String): SubscribeAddEvent()
    data class EnteredRssLink(val rss: String): SubscribeAddEvent()
    object SaveSubscribe: SubscribeAddEvent()
}
