package com.jeongg.ppap.presentation.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.data._const.DataStoreKey
import com.jeongg.ppap.data.util.PDataStore
import com.jeongg.ppap.domain.usecase.user.KakaoLogin
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.Resource
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val dataStore: PDataStore,
    private val kakaoLoginUseCase: KakaoLogin,
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun kakaoLogin(context: Context) {
        val fcmToken = dataStore.getData(DataStoreKey.FCM_TOKEN_KEY.name)
        val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
            if (error != null && token != null) {
                kakaoToServer(token.accessToken, fcmToken)
            }
        }
        if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
            UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                if (error != null) {
                    if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                        return@loginWithKakaoTalk
                    }
                    UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                }
                else if (token != null) {
                    kakaoToServer(token.accessToken, fcmToken)
                }
            }
        }
        else UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
    }

    private fun kakaoToServer(accessToken: String, fcmToken: String){
        viewModelScope.launch {
            kakaoLoginUseCase(accessToken, fcmToken).collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> _eventFlow.emit(PEvent.NAVIGATE)
                    is Resource.Error -> _eventFlow.emit(PEvent.TOAST(response.message))
                }
            }
        }
    }
}