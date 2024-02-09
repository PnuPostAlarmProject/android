package com.jeongg.ppap.data.dto.univ

import kotlinx.serialization.Serializable

@Serializable
data class UnivNoticeBoardDTO(
    val subscribeId: Long,
    val title: String,
    val link: String
)
