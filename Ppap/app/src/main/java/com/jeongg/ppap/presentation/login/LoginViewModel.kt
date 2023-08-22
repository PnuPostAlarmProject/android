package com.jeongg.ppap.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.data.user.UserRepository
import com.jeongg.ppap.data.util.KAKAO_REFRESH_KEY
import com.jeongg.ppap.data.util.KAKAO_TOKEN_KEY
import com.jeongg.ppap.data.util.PDataStore
import com.jeongg.ppap.util.log
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStore: PDataStore,
    private val userRepository: UserRepository
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<LoginUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private fun kakaoToServer(accessToken: String){
        viewModelScope.launch {
            _eventFlow.emit(LoginUiEvent.LoginLoading)
            val response = userRepository.kakaoLoginToServer(accessToken)
            if (response.success) {
                "로그인 전송 서버 성공".log()
                _eventFlow.emit(LoginUiEvent.LoginSuccess)
                dataStore.setData(KAKAO_TOKEN_KEY, response.response?.accessToken ?: "")
                dataStore.setData(KAKAO_REFRESH_KEY, response.response?.refreshToken ?: "")
            } else {
                "로그인 서버 전송 실패".log()
                _eventFlow.emit(LoginUiEvent.LoginFail(("로그인에 실패하였습니다.\n" + response.error?.message)))
            }
        }
    }
    private fun kakaoRefreshServer(refreshToken: String){
        viewModelScope.launch{
            _eventFlow.emit(LoginUiEvent.LoginLoading)
            val response = userRepository.kakaoReissue(refreshToken)
            if (response.success){
                dataStore.setData(KAKAO_TOKEN_KEY, response.response?.accessToken ?: "")
                dataStore.setData(KAKAO_REFRESH_KEY, response.response?.refreshToken ?: "")
            } else {
                "카카오 토큰 재발급 실패".log()
            }
        }
    }
    fun kakaoLogin(context: Context) {
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null) {
                    if (error is KakaoSdkError && error.isInvalidTokenError()) {
                        "로그인 필요1".log()
                        kakaoRefreshServer(dataStore.getData(KAKAO_REFRESH_KEY))
                        realKakaoLogin(context)
                    }
                    else "로그인 실패1".log()
                }
                else {
                    "로그인 필요2".log()
                    realKakaoLogin(context)
                }
            }
        }
        else {
            "로그인 필요3".log()
            realKakaoLogin(context)
        }
    }

    private fun realKakaoLogin(context: Context) {
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                "카카오계정으로 로그인 실패".log()
            } else if (token != null) {
                "카카오계정으로 로그인 성공 ${token.accessToken}".log()
                kakaoToServer(token.accessToken)
            }
        }
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    "카카오톡으로 로그인 실패: ${error.message}".log()
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                } else if (token != null) {
                    "카카오톡으로 로그인 성공 ${token.accessToken}".log()
                    kakaoToServer(token.accessToken)
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }
}