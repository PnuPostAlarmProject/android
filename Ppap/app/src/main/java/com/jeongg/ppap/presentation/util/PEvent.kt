package com.jeongg.ppap.presentation.util

sealed class PEvent{
    object SUCCESS: PEvent()
    object GET: PEvent()
    object UPDATE: PEvent()
    object ADD: PEvent()
    object DELETE: PEvent()
    object LOADING: PEvent()
    object EMPTY: PEvent()

    data class ERROR(val message: String): PEvent()
}