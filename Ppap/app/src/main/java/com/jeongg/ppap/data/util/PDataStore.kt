package com.jeongg.ppap.data.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.jeongg.ppap.dataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

class PDataStore @Inject constructor(@ApplicationContext val context: Context) {
    private val store = context.dataStore
    suspend fun setData(key: String, value: String){
        runBlocking(Dispatchers.IO) {
            store.edit{ it[stringPreferencesKey(key)] = value }
        }
    }
    fun getData(key: String): String {
        return runBlocking(Dispatchers.IO){
            store.data.map{ it[stringPreferencesKey(key)] ?: "null" }.first()
        }
    }
}

const val FCM_TOKEN_KEY = "fcm-token"
const val KAKAO_TOKEN_KEY = "kakao-token"
const val KAKAO_REFRESH_KEY = "kakao-refresh-token"