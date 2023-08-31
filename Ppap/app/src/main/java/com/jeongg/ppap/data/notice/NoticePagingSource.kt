package com.jeongg.ppap.data.notice

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jeongg.ppap.data.notice.dto.ContentScrapDTO


class NoticePagingSource(
    private val service: NoticeService,
    private val subscribeId: Long?
): PagingSource<Int, ContentScrapDTO>() {

    companion object {
        private const val STARTING_PAGE_INDEX = 1
    }
    override fun getRefreshKey(state: PagingState<Int, ContentScrapDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ContentScrapDTO> {
        val page = params.key ?: STARTING_PAGE_INDEX
        return try {
            val response = service.getNoticeList(subscribeId, page)
            if (response.success) {
                val contents = response.response?.contents ?: emptyList()
                LoadResult.Page(
                    data = contents,
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                    nextKey = if (contents.isEmpty()) null else page + 1
                )
            } else {
                LoadResult.Error(Exception(response.error?.message ?: "공지 리스트를 불러오는데 실패하였습니다."))
            }
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}