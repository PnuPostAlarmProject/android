package com.jeongg.ppap

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.preferences.preferencesDataStore
import com.jeongg.ppap.presentation.PpapAppTheme
import dagger.hilt.android.AndroidEntryPoint


val Context.dataStore by preferencesDataStore("ppap_data")

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        openNoticeLink()
        setContent { PpapAppTheme() }
    }

    private fun openNoticeLink() {
        try {
            if (intent?.extras != null) {
                val link = intent.extras!!.get("link").toString()
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                startActivity(browserIntent)
            }
        } catch(_: Exception){ }
    }
}
