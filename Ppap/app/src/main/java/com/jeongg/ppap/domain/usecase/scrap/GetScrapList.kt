package com.jeongg.ppap.domain.usecase.scrap

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.jeongg.ppap.data.dto.ScrapDTO
import com.jeongg.ppap.data.paging.ScrapPagingSource
import com.jeongg.ppap.data.util.HttpRoutes
import com.jeongg.ppap.domain.repository.ScrapRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetScrapList @Inject constructor(
    private val scrapRepository: ScrapRepository
){
    operator fun invoke(subscribeId: Long?): Flow<PagingData<ScrapDTO>> {
        return Pager(
            config = PagingConfig(enablePlaceholders = false, pageSize = HttpRoutes.PER_PAGE_SIZE, prefetchDistance = 1),
            pagingSourceFactory = { ScrapPagingSource(scrapRepository, subscribeId) }
        ).flow
    }
}