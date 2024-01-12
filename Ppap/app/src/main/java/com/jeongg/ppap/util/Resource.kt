package com.jeongg.ppap.util

sealed class Resource<T>(val data: T? = null, val message: String = "예상치 못한 에러가 발생했습니다.") {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Loading<T>(data: T? = null) : Resource<T>(data)
}