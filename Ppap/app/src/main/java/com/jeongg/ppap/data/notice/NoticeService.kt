package com.jeongg.ppap.data.notice

import com.jeongg.ppap.data.notice.dto.NoticeWithSubscribeDTO
import com.jeongg.ppap.data.util.ApiUtils

interface NoticeService {
    suspend fun getNoticeList(subscribeId: Long?, page: Int?): ApiUtils.ApiResult<NoticeWithSubscribeDTO>
}