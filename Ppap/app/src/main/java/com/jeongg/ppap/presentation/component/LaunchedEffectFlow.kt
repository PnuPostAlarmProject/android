package com.jeongg.ppap.presentation.component

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.jeongg.ppap.presentation.util.PEvent
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun LaunchedEffectEvent(
    eventFlow: SharedFlow<PEvent>,
    onNavigate: () -> Unit = {},
) {
    val context = LocalContext.current
    LaunchedEffect(key1 = true) {
        eventFlow.collectLatest { event ->
            when (event) {
                is PEvent.NAVIGATE -> onNavigate()
                is PEvent.TOAST -> Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                is PEvent.LOADING, PEvent.SUCCESS -> { }
            }
        }
    }
}