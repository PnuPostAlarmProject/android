package com.jeongg.ppap.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.jeongg.ppap.data._const.PagingConst
import com.jeongg.ppap.data.dto.ScrapDTO
import com.jeongg.ppap.data.dto.ScrapListDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.domain.repository.ScrapRepository
import io.ktor.client.call.body
import io.ktor.http.HttpStatusCode

class ScrapPagingSource(
    private val scrapRepository: ScrapRepository,
    private val subscribeId: Long?
): PagingSource<Int, ScrapDTO>() {

    override fun getRefreshKey(state: PagingState<Int, ScrapDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ScrapDTO> {
        return try {
            val page = params.key ?: PagingConst.STARTING_PAGE_INDEX.value
            val response = scrapRepository.getScrapList(subscribeId, page)
            val body = response.body<ApiUtils.ApiResult<ScrapListDTO>>()
            val contents = body.response?.scraps ?: emptyList()
            val errorMessage = body.error?.message ?: "스크랩 리스트를 불러오는데 실패하였습니다."

            if (response.status == HttpStatusCode.OK && body.success) {
                LoadResult.Page(
                    data = contents,
                    prevKey = if (page == PagingConst.STARTING_PAGE_INDEX.value) null else page - 1,
                    nextKey = if (contents.isEmpty()) null else page + 1
                )
            }
            else LoadResult.Error(Exception(errorMessage))
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }
}