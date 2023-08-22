package com.jeongg.ppap.data.user

import com.jeongg.ppap.data.user.dto.KakaoLoginDTO
import com.jeongg.ppap.data.util.ApiUtils

interface UserRepository {
    suspend fun kakaoLogin(accessToken: String): ApiUtils.ApiResult<KakaoLoginDTO>
    suspend fun kakaoReissue(refreshToken: String): ApiUtils.ApiResult<KakaoLoginDTO>
    suspend fun logout(): ApiUtils.ApiResult<KakaoLoginDTO>
}