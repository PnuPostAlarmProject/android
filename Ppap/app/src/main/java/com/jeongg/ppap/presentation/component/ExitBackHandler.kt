package com.jeongg.ppap.presentation.component

import android.app.Activity
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import kotlinx.coroutines.delay

@Composable
fun ExitBackHandler() {
    val context = LocalContext.current
    val canFinish = remember { mutableStateOf(false) }

    LaunchedEffect(canFinish.value) {
        if (canFinish.value) {
            delay(3000)
            canFinish.value = false
        }
    }
    BackHandler(enabled = true) {
        if (canFinish.value) {
            // 앱 종료
            (context as? Activity)?.finish()
        }
        else {
            Toast.makeText(context, "앱을 종료하고 싶다면\n뒤로 가기를 한 번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
            canFinish.value = true
        }
    }
}