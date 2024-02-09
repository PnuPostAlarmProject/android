package com.jeongg.ppap.data.dto.scrap

import kotlinx.serialization.Serializable

@Serializable
data class ScrapListDTO(
    val curSubscribeId: Long,
    val scraps: List<ScrapDTO>
)
