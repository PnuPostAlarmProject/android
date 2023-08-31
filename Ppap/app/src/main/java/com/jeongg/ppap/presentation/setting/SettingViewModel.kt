package com.jeongg.ppap.presentation.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.data.user.UserRepository
import com.jeongg.ppap.data.util.ACCESS_TOKEN_KEY
import com.jeongg.ppap.data.util.PDataStore
import com.jeongg.ppap.data.util.REFRESH_TOKEN_KEY
import com.jeongg.ppap.presentation.util.PEvent
import com.jeongg.ppap.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingViewModel @Inject constructor(
    private val dataStore: PDataStore,
    private val userRepository: UserRepository
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    fun logout(){
        viewModelScope.launch {
            _eventFlow.emit(PEvent.LOADING)
            val response = userRepository.logout()
            if (response.success) {
                "로그인 아웃 성공".log()
                dataStore.setData(ACCESS_TOKEN_KEY, "")
                dataStore.setData(REFRESH_TOKEN_KEY, "")
                _eventFlow.emit(PEvent.SUCCESS)
            } else {
                "로그아웃 실패".log()
                _eventFlow.emit(PEvent.ERROR((response.error?.message ?: "로그아웃 실패하였습니다.")))
            }
        }
    }
    fun withdraw(){
        viewModelScope.launch {
            _eventFlow.emit(PEvent.LOADING)
            val response = userRepository.withdraw()
            if (response.success) {
                "회원탈퇴 아웃 성공".log()
                dataStore.setData(ACCESS_TOKEN_KEY, "")
                dataStore.setData(REFRESH_TOKEN_KEY, "")
                _eventFlow.emit(PEvent.SUCCESS)
            } else {
                "회원탈퇴 실패".log()
                _eventFlow.emit(PEvent.ERROR((response.error?.message ?: "회원탈퇴 실패하였습니다.")))
            }
        }
    }
}