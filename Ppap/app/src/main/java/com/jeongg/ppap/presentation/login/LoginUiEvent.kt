package com.jeongg.ppap.presentation.login

sealed class LoginUiEvent {
    object LoginSuccess: LoginUiEvent()
    data class LoginFail(val message: String): LoginUiEvent()
    object LoginLoading: LoginUiEvent()
}
