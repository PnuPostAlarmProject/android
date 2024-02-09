package com.jeongg.ppap.data.dto.notice

import kotlinx.serialization.Serializable

@Serializable
data class NoticeListDTO(
    val curSubscribeId: Long,
    val contents: List<NoticeDTO>
)
