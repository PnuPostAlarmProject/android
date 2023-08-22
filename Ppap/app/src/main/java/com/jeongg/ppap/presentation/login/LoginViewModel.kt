package com.jeongg.ppap.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.data.util.KAKAO_REFRESH_KEY
import com.jeongg.ppap.data.util.KAKAO_TOKEN_KEY
import com.jeongg.ppap.data.util.PDataStore
import com.jeongg.ppap.domain.usecase.user.UserUseCases
import com.jeongg.ppap.util.Resource
import com.jeongg.ppap.util.log
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStore: PDataStore,
    private val userUseCases: UserUseCases,
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<LoginUiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()
/*
    fun kakaoLogin(){
        // 카카오계정으로 로그인 공통 callback 구성
        // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null) {
                "카카오계정으로 로그인 실패".log()
            } else if (token != null) {
                "카카오계정으로 로그인 성공".log()
            }
        }

        // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    "카카오톡으로 로그인 실패".log()

                    // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                    // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }

                    // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                    UserApiClient.instance.loginWithKakaoAccount(this, callback = callback)
                } else if (token != null) {
                    "카카오톡으로 로그인 성공 ${token.accessToken}".log()
                }
            }
        } else {
            UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
        }
    }*/
    fun reissue(){
        val refreshToken = dataStore.getData(KAKAO_REFRESH_KEY)
        viewModelScope.launch {
            userUseCases.reissue(refreshToken).collect{ result ->
                when(result){
                    is Resource.Success -> {
                        dataStore.setData(KAKAO_TOKEN_KEY, result.data?.response?.accessToken ?: "empty-token")
                        dataStore.setData(KAKAO_REFRESH_KEY, result.data?.response?.refreshToken ?: "empty-refresh-token")
                    }
                    is Resource.Error -> {
                        "토큰 재발급 실패".log()
                    }
                    is Resource.Loading -> {
                        "토큰 재발급 로딩중".log()
                    }
                }
            }
        }
    }
    fun tokenToServer(token: String){
        viewModelScope.launch {
            userUseCases.kakaoLogin(token).collect{ result ->
                when (result){
                    is Resource.Success -> {
                        "login success".log()
                        _eventFlow.emit(LoginUiEvent.LoginSuccess)
                        dataStore.setData(KAKAO_TOKEN_KEY, token)
                    }
                    is Resource.Error -> {
                        "login fail: ${result.message}".log()
                        _eventFlow.emit(LoginUiEvent.LoginFail(result.message ?: "로그인에 실패하였습니다."))
                        reissue()
                    }
                    is Resource.Loading -> {
                        _eventFlow.emit(LoginUiEvent.LoginLoading)
                    }
                }
            }
        }
    }
}