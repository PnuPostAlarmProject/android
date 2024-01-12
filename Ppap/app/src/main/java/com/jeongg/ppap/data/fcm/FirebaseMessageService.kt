package com.jeongg.ppap.data.fcm

import android.content.Intent
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.jeongg.ppap.MainActivity
import com.jeongg.ppap.data.util.FCM_TOKEN_KEY
import com.jeongg.ppap.dataStore
import com.jeongg.ppap.util.log
import kotlinx.coroutines.runBlocking

class FirebaseMessageService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        if (message.notification != null){
            val title = message.notification?.title ?: "empty-title"
            val body = message.notification?.body ?: "empty-message"
            "title: $title, body: $body".log()

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

    override fun onNewToken(token: String) {
        val key = stringPreferencesKey(FCM_TOKEN_KEY)
        runBlocking {
            dataStore.edit{ it[key] = token }
        }
        "new token is $token".log()
        super.onNewToken(token)
    }

}