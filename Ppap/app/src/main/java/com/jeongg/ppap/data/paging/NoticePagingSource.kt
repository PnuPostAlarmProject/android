package com.jeongg.ppap.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jeongg.ppap.data.dto.NoticeDTO
import com.jeongg.ppap.data.dto.NoticeListDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.HttpRoutes.STARTING_PAGE_INDEX
import com.jeongg.ppap.domain.repository.NoticeRepository
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class NoticePagingSource(
    private val noticeRepository: NoticeRepository,
    private val subscribeId: Long?
): PagingSource<Int, NoticeDTO>() {

    override fun getRefreshKey(state: PagingState<Int, NoticeDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, NoticeDTO> {
        return try {
            val page = params.key ?: STARTING_PAGE_INDEX
            val response = noticeRepository.getNoticeList(subscribeId, page)
            val body = response.body<ApiUtils.ApiResult<NoticeListDTO>>()
            val contents = body.response?.contents ?: emptyList()
            val errorMessage = body.error?.message ?: "공지 리스트를 불러오는데 실패하였습니다."

            if (response.status == HttpStatusCode.OK && body.success) {
                LoadResult.Page(
                    data = contents,
                    prevKey = if (page == STARTING_PAGE_INDEX) null else page - 1,
                    nextKey = if (contents.isEmpty()) null else page + 1
                )
            }
            else LoadResult.Error(Exception(errorMessage))
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}