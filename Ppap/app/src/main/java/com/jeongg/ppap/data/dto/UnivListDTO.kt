package com.jeongg.ppap.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class UnivListDTO(
    val univ: String = "",
    val departments: List<UnivDepartmentDTO> = emptyList()
)