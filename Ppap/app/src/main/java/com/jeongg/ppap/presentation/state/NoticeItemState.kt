package com.jeongg.ppap.presentation.state

import androidx.paging.PagingData
import com.jeongg.ppap.data.dto.notice.NoticeItemDTO
import com.jeongg.ppap.data.dto.subscribe.SubscribeGetResponseDTO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

data class NoticeItemState(
    val isLoading: Boolean = true,
    val subscribes: List<SubscribeGetResponseDTO> = emptyList(),
    val contents: Flow<PagingData<NoticeItemDTO>> = flow {},
    val errorMessage: String = "",
)