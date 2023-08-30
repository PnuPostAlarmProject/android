package com.jeongg.ppap.presentation.splash

import androidx.lifecycle.ViewModel
import com.jeongg.ppap.data.util.ACCESS_TOKEN_KEY
import com.jeongg.ppap.data.util.PDataStore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val dataStore: PDataStore
):ViewModel() {

    fun isUserLoggedIn(): Boolean{
        val token = dataStore.getData(ACCESS_TOKEN_KEY)
        return token.isNotBlank()
    }

}