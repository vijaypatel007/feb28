package com.example.feb28.dataClass

sealed class ResponseBody<T>(var data: T? = null, var message: String? = null) {
    class Success<T>(data: T) : ResponseBody<T>(data = data, null)
    class Error<T>(error: String) : ResponseBody<T>(null, error)
    class Loading<T>() : ResponseBody<T>()
}