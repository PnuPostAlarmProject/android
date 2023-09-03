package com.jeongg.ppap.data.scrap

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jeongg.ppap.data.scrap.dto.ScrapDTO
import com.jeongg.ppap.data.util.HttpRoutes.STARTING_PAGE_INDEX

class ScrapPagingSource(
    private val service: ScrapService,
    private val subscribeId: Long?
): PagingSource<Int, ScrapDTO>() {

    override fun getRefreshKey(state: PagingState<Int, ScrapDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ScrapDTO> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getScrapList(subscribeId, page)
            if (response.success) {
                val contents = response.response?.scraps ?: emptyList()
                LoadResult.Page(
                    data = contents,
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                    nextKey = if (contents.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Exception(response.error?.message ?: "스크랩 리스트를 불러오는데 실패하였습니다."))
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}