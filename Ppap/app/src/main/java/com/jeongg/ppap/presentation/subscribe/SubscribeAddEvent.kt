package com.jeongg.ppap.presentation.subscribe

sealed class SubscribeAddEvent{
    data class EnteredTitle(val title: String): SubscribeAddEvent()
    data class EnteredNoticeLink(val notice: String?): SubscribeAddEvent()
    data class EnteredRssLink(val rss: String): SubscribeAddEvent()
    object SaveSubscribe: SubscribeAddEvent()
}
