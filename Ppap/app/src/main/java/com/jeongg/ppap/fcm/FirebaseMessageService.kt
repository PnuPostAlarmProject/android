package com.jeongg.ppap.fcm

import android.content.Intent
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jeongg.ppap.MainActivity
import com.jeongg.ppap.dataStore
import com.jeongg.ppap.util.log
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class FirebaseMessageService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        val title = message.notification?.title ?: "empty-title"
        "title: $title".log()

        if (message.data.isNotEmpty()){
            val key1 = message.data["key1"] ?: "key1-empty"
            val m = message.notification?.body ?: "message=empty"
            "message: $m".log()
        }
        if (message.notification != null){
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
            "onMessageReceived $token".log()
        })
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onNewToken(token: String) {
        val key = stringPreferencesKey("fcm_token")
        GlobalScope.launch {
            baseContext.dataStore.edit{ pref ->
                pref[key] = token
            }
        }
        "new token is $token".log()
        super.onNewToken(token)
    }

}