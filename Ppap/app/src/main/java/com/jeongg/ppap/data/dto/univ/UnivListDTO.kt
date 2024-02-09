package com.jeongg.ppap.data.dto.univ

import kotlinx.serialization.Serializable

@Serializable
data class UnivListDTO(
    val univ: String = "",
    val departments: List<UnivDepartmentDTO> = emptyList()
)