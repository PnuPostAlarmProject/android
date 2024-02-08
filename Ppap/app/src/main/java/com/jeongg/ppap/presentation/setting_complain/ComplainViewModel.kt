package com.jeongg.ppap.presentation.setting_complain

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.jeongg.ppap.data.smtp.SendEmail
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ComplainViewModel @Inject constructor(
    private val sendEmail: SendEmail
): ViewModel() {

    private val _content = mutableStateOf("")
    val content = _content

    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading

    fun enterContent(text: String){
        _content.value = text
    }

    fun sendEmail(onSuccess: () -> Unit){
        _isLoading.value = true
        sendEmail.sendEmail(
            content = content.value,
            onSuccess = onSuccess,
            onFinish = { _isLoading.value = false }
        )
    }
}