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
    suspend fun kakaoLoginToServer(kakaoToken: String, fcmTokenDTO: String): ApiUtils.ApiResult<KakaoLoginDTO> {
        return try {
            userService.kakaoLogin(kakaoToken, fcmTokenDTO)
        } catch(e: Exception) {
            "kakao-login-fail: ${e.message}".log()
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }
    suspend fun kakaoReissue(refreshToken: String): ApiUtils.ApiResult<KakaoLoginDTO> {
        return try {
            userService.reissue(refreshToken)
        } catch(e: Exception){
            "kakao-refresh-fail: ${e.message}".log()
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }
    suspend fun logout(): ApiUtils.ApiResult<KakaoLoginDTO> {
        return try {
            userService.logout()
        } catch(e: Exception){
            "kakao-logout-fail: ${e.message}".log()
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }
    suspend fun withdraw(): ApiUtils.ApiResult<String>{
        return try {
            userService.withdraw()
        } catch(e: Exception){
            "kakao-withdraw-fail: ${e.message}".log()
            val error = getErrorMessage(e)
            ApiUtils.ApiResult(false, null, ApiUtils.ApiError(error.first, error.second))
        }
    }
}