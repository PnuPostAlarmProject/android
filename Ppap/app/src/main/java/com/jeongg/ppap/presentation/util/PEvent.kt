package com.jeongg.ppap.presentation.util

sealed class PEvent{
    object SUCCESS: PEvent()
    object LOADING: PEvent()
    object NAVIGATE: PEvent()
    data class TOAST(val message: String): PEvent()
}