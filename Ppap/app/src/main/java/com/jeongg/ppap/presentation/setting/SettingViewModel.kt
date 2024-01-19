package com.jeongg.ppap.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.domain.usecase.user.Logout
import com.jeongg.ppap.domain.usecase.user.Withdrawal
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.Resource
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val logoutUseCase: Logout,
    private val withdrawalUseCase: Withdrawal,
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun logout(){
        viewModelScope.launch {
            logoutUseCase().collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> {
                        kakaoLogout()
                        _eventFlow.emit(PEvent.SUCCESS)
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.ERROR(response.message))
                }
            }
        }
    }
    fun withdrawl(){
        viewModelScope.launch {
            withdrawalUseCase().collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> {
                        kakaoLogout()
                        _eventFlow.emit(PEvent.SUCCESS)
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.ERROR(response.message))
                }
            }
        }
    }
    private fun kakaoLogout() {
        UserApiClient.instance.logout {}
    }
}