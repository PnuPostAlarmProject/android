package com.jeongg.ppap.data.fcm

import android.content.Intent
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jeongg.ppap.MainActivity
import com.jeongg.ppap.data._const.DataStoreKey
import com.jeongg.ppap.dataStore
import kotlinx.coroutines.runBlocking

class FirebaseMessageService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.notification != null){
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
        })
    }

    override fun onNewToken(token: String) {
        val key = stringPreferencesKey(DataStoreKey.FCM_TOKEN_KEY.name)
        runBlocking {
            dataStore.edit{ it[key] = token }
        }
        super.onNewToken(token)
    }

}