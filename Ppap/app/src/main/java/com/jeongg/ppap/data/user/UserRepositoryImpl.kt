package com.jeongg.ppap.data.user

import com.jeongg.ppap.data.user.dto.KakaoLoginDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.FCM_TOKEN_KEY
import com.jeongg.ppap.data.util.KAKAO_TOKEN_KEY
import com.jeongg.ppap.data.util.PDataStore
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val userService: UserService,
): UserRepository {
    override suspend fun kakaoLogin(accessToken: String): ApiUtils.ApiResult<KakaoLoginDTO>{
        return userService.kakaoLogin(accessToken)
    }

    override suspend fun kakaoReissue(refreshToken: String): ApiUtils.ApiResult<KakaoLoginDTO> {
        return userService.reissue(refreshToken)
    }

    override suspend fun logout(): ApiUtils.ApiResult<KakaoLoginDTO> {
        return userService.logout()
    }
}