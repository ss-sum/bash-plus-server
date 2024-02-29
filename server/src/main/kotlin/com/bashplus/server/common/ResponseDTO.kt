package com.bashplus.server.common

class ResponseDTO(message: Any = "") {
    private val message: Any = message
    fun getMessage(): Any {
        return message
    }
}