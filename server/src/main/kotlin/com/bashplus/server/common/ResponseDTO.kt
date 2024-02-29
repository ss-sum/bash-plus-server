package com.bashplus.server.common

class ResponseDTO(message: String = "") {
    private val message: String = message
    fun getMessage(): String {
        return message
    }
}