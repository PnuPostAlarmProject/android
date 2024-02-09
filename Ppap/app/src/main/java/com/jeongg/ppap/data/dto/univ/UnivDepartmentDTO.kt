package com.jeongg.ppap.data.dto.univ

import kotlinx.serialization.Serializable

@Serializable
data class UnivDepartmentDTO(
    val univId: Long,
    val name: String
)
