package com.jeongg.ppap

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.jeongg.ppap.presentation.PpapAppTheme
import dagger.hilt.android.AndroidEntryPoint


val Context.dataStore by preferencesDataStore("ppap_data")

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var firebaseAnalytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        firebaseAnalytics = FirebaseAnalytics.getInstance(this)
        openNoticeLink()
        setContent {
            val navController = rememberNavController()
            setupScreenTracking(navController)
            PpapAppTheme(navController)
        }
    }

    private fun openNoticeLink() {
        try {
            if (intent?.extras != null) {
                val link = intent.extras!!.get("link").toString()
                val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
                intent.extras!!.clear()
                startActivity(browserIntent)
            }
        } catch(_: Exception){ }
    }

    private fun setupScreenTracking(navController: NavController) {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            val params = Bundle()
            params.putString(FirebaseAnalytics.Param.SCREEN_NAME, destination.route)
            firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
        }
    }
}
