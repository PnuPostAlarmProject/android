package com.jeongg.ppap.data.notice.dto

import kotlinx.serialization.Serializable

@Serializable
data class NoticeWithSubscribeDTO(
    val subscribes: List<SubscribeDTO>,
    val curSubscribeId: Long,
    val contents: List<NoticeDTO>
)
