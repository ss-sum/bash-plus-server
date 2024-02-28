package com.bashplus.server.common.exception

class ApiException(private val error: ExceptionEnum) : RuntimeException(error.getMessage()) {

    open fun getError(): ExceptionEnum {
        return this.error;
    }
}