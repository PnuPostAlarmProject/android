package com.jeongg.ppap.presentation.subscribe_custom_add

sealed class SubscribeCustomAddEvent{
    data class EnteredTitle(val title: String): SubscribeCustomAddEvent()
    data class EnteredRssLink(val rss: String): SubscribeCustomAddEvent()
    object SaveSubscribe: SubscribeCustomAddEvent()
}
