package com.jeongg.ppap

import android.app.Application
import com.jeongg.ppap.BuildConfig.KAKAO_NATIVE_APP_KEY
import com.kakao.sdk.common.KakaoSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class PpapApp: Application() {
    override fun onCreate() {
        super.onCreate()
        KakaoSdk.init(this, KAKAO_NATIVE_APP_KEY)    }
}