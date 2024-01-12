package com.jeongg.ppap.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.data.util.FCM_TOKEN_KEY
import com.jeongg.ppap.data.util.PDataStore
import com.jeongg.ppap.domain.usecase.user.KakaoLogin
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.Resource
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
    private val kakaoLoginUsecase: KakaoLogin
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun kakaoLogin(context: Context) {
        if (AuthApiClient.instance.hasToken()) {
            UserApiClient.instance.accessTokenInfo { _, error ->
                if (error != null && error is KakaoSdkError && error.isInvalidTokenError()) {
                    realKakaoLogin(context)
                }
            }
        }
        else realKakaoLogin(context)
    }

    private fun kakaoToServer(accessToken: String, fcmToken: String){
        viewModelScope.launch {
            kakaoLoginUsecase(accessToken, fcmToken).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> _eventFlow.emit(PEvent.SUCCESS)
                    is Resource.Error -> _eventFlow.emit(PEvent.ERROR(response.message))
                }
            }
        }
    }
    private fun realKakaoLogin(context: Context) {
        val fcmtoken = dataStore.getData(FCM_TOKEN_KEY)
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null && token != null) {
                kakaoToServer(token.accessToken, fcmtoken)
            }
        }
        "realKakao".log()
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                "realKakao2".log()
                if (error != null) {
                    "realKakao3".log()
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    "realKakao4".log()
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                }
                else if (token != null) {
                    "realKakao5".log()
                    kakaoToServer(token.accessToken, fcmtoken)
                }
            }
        }
        else UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }
}