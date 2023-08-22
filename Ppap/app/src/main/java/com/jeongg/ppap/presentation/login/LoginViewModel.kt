package com.jeongg.ppap.presentation.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.data.user.UserService
import com.jeongg.ppap.util.log
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val userService: UserService
): ViewModel() {

    fun tokenToServer(){
        viewModelScope.launch {
            val result = userService.kakaoLogin("test-token")
            if (result.success){
                val data = result.response?.accessToken ?: "empty-token"
                "kakao-token is $data".log()
            }
            else {
                "result: ${result.error?.message}".log()
                "error in kakaologin".log()
            }
        }
    }
}