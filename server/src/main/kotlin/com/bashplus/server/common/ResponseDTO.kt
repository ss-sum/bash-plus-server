package com.bashplus.server.common

class ResponseDTO<T>(message: T) {
    private val message: T = message
    fun getMessage(): T {
        return message
    }
}