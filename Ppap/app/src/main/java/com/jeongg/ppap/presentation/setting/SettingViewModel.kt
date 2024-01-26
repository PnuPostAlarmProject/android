package com.jeongg.ppap.presentation.setting

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.provider.Settings
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jeongg.ppap.BuildConfig
import com.jeongg.ppap.data.util.DARK_THEME
import com.jeongg.ppap.data.util.LIGHT_THEME
import com.jeongg.ppap.data.util.PDataStore
import com.jeongg.ppap.data.util.PPAP_THEME_KEY
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
    private val dataStore: PDataStore,
    private val logoutUseCase: Logout,
    private val withdrawalUseCase: Withdrawal,
): ViewModel() {

    private val _eventFlow = MutableSharedFlow<PEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    private val _email = mutableStateOf("")
    val email = _email

    val version = BuildConfig.VERSION_NAME

    private val _theme = mutableStateOf("")
    val theme = _theme

    init {
        getUserEmail()
        getTheme()
    }

    fun logout(){
        viewModelScope.launch {
            logoutUseCase().collect { response ->
                when(response){
                    is Resource.Loading -> _eventFlow.emit(PEvent.LOADING)
                    is Resource.Success -> {
                        kakaoLogout()
                        _eventFlow.emit(PEvent.NAVIGATE)
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.TOAST(response.message))
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
                        _eventFlow.emit(PEvent.NAVIGATE)
                    }
                    is Resource.Error -> _eventFlow.emit(PEvent.TOAST(response.message))
                }
            }
        }
    }
    fun presentNotificationSetting(context: Context) {
        val intent = notificationSettingOreo(context)
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            e.printStackTrace()
        }
    }
    fun changeAppTheme(newTheme: String){
        dataStore.setData(PPAP_THEME_KEY, newTheme)
        theme.value = themeToString(newTheme)
    }
    private fun getTheme(){
        val dataStoreTheme = dataStore.getData(PPAP_THEME_KEY)
        _theme.value = themeToString(dataStoreTheme)
    }

    private fun themeToString(dataStoreTheme: String) = when (dataStoreTheme) {
        DARK_THEME -> "어두운 테마"
        LIGHT_THEME -> "밝은 테마"
        else -> "시스템 설정"
    }

    private fun notificationSettingOreo(context: Context): Intent {
        return Intent().also { intent ->
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.packageName)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
    }
    private fun getUserEmail(){
        UserApiClient.instance.me { user, error ->
            if (error == null && user != null){
                _email.value = user.kakaoAccount?.email ?: "이메일을 확인할 수 없습니다."
            }
        }
    }
    private fun kakaoLogout() {
        UserApiClient.instance.logout {}
    }
}