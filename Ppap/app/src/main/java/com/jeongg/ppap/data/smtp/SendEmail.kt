package com.jeongg.ppap.data.smtp

import android.content.Context
import android.widget.Toast
import com.jeongg.ppap.BuildConfig
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.Properties
import javax.inject.Inject
import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SendEmail @Inject constructor(
    @ApplicationContext val context: Context
){
    fun sendEmail(
        content: String,
        onSuccess: () -> Unit = {},
        onFinish: () -> Unit = {}
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val host = "smtp.gmail.com"
            val port = 587
            val subject = "[PPAP] 건의사항"
            val username = BuildConfig.EMAIL
            val password = BuildConfig.PASSWORD

            val props = Properties()
            props["mail.smtp.auth"] = "true"
            props["mail.smtp.starttls.enable"] = "true"
            props["mail.smtp.host"] = host
            props["mail.smtp.port"] = port

            val session = Session.getInstance(props, object : Authenticator() {
                override fun getPasswordAuthentication(): PasswordAuthentication {
                    return PasswordAuthentication(username, password)
                }
            })

            try {
                if (content.isEmpty()){
                    CoroutineScope(Dispatchers.Main).launch {
                        onFinish.invoke()
                        Toast.makeText(context, "건의사항은 비어있으면 안됩니다.", Toast.LENGTH_SHORT).show()
                    }
                    return@launch
                }

                val message = MimeMessage(session)
                message.setFrom(InternetAddress(username))
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(username))
                message.subject = subject
                message.setText(content)
                Transport.send(message)

                CoroutineScope(Dispatchers.Main).launch {
                    Toast.makeText(context, "성공적으로 전송되었습니다.", Toast.LENGTH_SHORT).show()
                    onFinish.invoke()
                    onSuccess.invoke()
                }
            } catch (_: Exception) {
                CoroutineScope(Dispatchers.Main).launch {
                    onFinish.invoke()
                    Toast.makeText(context, "메시지 전송에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}