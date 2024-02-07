package com.jeongg.ppap.domain.usecase.notice

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jeongg.ppap.data.dto.NoticeDTO
import com.jeongg.ppap.data.paging.NoticePagingSource
import com.jeongg.ppap.data._const.PagingConst
import com.jeongg.ppap.domain.repository.NoticeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNoticeList @Inject constructor(
    private val noticeRepository: NoticeRepository
) {
    operator fun invoke(subscribeId: Long?): Flow<PagingData<NoticeDTO>> {
        return Pager(
            config = PagingConfig(
                enablePlaceholders = false,
                pageSize = PagingConst.PER_PAGE_SIZE.value,
                prefetchDistance = PagingConst.PREFETCH_DISTANCE.value
            ),
            pagingSourceFactory = { NoticePagingSource(noticeRepository, subscribeId) }
        ).flow
    }
}