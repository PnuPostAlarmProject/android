package com.jeongg.ppap.data.util

import kotlinx.serialization.Serializable


class ApiUtils {
    @Serializable data class ApiResult<T>(val success: Boolean, val response: T?, val error: ApiError?)
    @Serializable data class ApiError(val message: String, val status: Int)
}