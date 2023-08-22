package com.jeongg.ppap.data.user

import com.jeongg.ppap.data.user.dto.KakaoLoginDTO
import com.jeongg.ppap.data.util.ApiUtils
import com.jeongg.ppap.data.util.getErrorMessage
import com.jeongg.ppap.util.log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userService: UserService,
) {
    suspend fun kakaoLoginToServer(accessToken: String): ApiUtils.ApiResult<KakaoLoginDTO> {
        return try {
            userService.kakaoLogin(accessToken)
        } catch(e: Exception){
            "kakaologin: ${e.message}".log()
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }
    suspend fun kakaoReissue(refreshToken: String): ApiUtils.ApiResult<KakaoLoginDTO> {
        return try {
            userService.reissue(refreshToken)
        } catch(e: Exception){
            "kakao-refresh: ${e.message}".log()
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }
    suspend fun logout(): ApiUtils.ApiResult<KakaoLoginDTO> {
        return try {
            userService.logout()
        } catch(e: Exception){
            "kakao-logout: ${e.message}".log()
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }
}