package com.jeongg.ppap.data.scrap.dto

import com.jeongg.ppap.data.notice.dto.SubscribeDTO
import kotlinx.serialization.Serializable

@Serializable
data class ScrapWithSubscribeDTO(
    val subscribes: List<SubscribeDTO>,
    val curSubscribeId: Long,
    val scraps: List<ScrapDTO>
)
