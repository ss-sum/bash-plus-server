package com.bashplus.server.common.exception

class ApiExceptionEntity(private val code: Int, private val message: String) {
    open fun getCode(): Int {
        return this.code;
    }

    open fun getMessage(): String {
        return this.message;
    }
}