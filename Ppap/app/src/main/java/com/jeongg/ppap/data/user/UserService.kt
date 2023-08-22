package com.jeongg.ppap.data.user

import com.jeongg.ppap.data.user.dto.KakaoLoginDTO
import com.jeongg.ppap.data.util.ApiUtils

interface UserService {
    suspend fun kakaoLogin(accessToken: String): ApiUtils.ApiResult<KakaoLoginDTO>
}
/**
 * domain:
 * data:repositories, data sources(network)
 * presentation: view ,viewmodel, state, event
 */