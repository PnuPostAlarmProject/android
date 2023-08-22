package com.jeongg.ppap.data.user

import com.jeongg.ppap.data.user.dto.KakaoLoginDTO
import com.jeongg.ppap.data.util.ApiUtils

interface UserService {
    suspend fun kakaoLogin(accessToken: String): ApiUtils.ApiResult<KakaoLoginDTO>
    suspend fun reissue(refreshToken: String): ApiUtils.ApiResult<KakaoLoginDTO>
    suspend fun logout(): ApiUtils.ApiResult<KakaoLoginDTO>
}