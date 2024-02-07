package com.jeongg.ppap.presentation.util

sealed class PEvent{
    object Loading: PEvent()
    object Navigate: PEvent()
    data class MakeToast(val message: String): PEvent()
}