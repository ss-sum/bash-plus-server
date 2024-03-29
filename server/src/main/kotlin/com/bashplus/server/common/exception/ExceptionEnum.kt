package com.bashplus.server.common.exception

import org.springframework.http.HttpStatus

enum class ExceptionEnum(
        private val status: HttpStatus,
        private val code: Int,
        private var message: String? = null) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal Server Error"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, 403, "Unauthorized"),
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 401, "Expired Token"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 401, "Invalid Token"),
    TOKEN_ERROR(HttpStatus.BAD_REQUEST, 400, "Token Error"), ;

    open fun getMessage(): String? {
        return this.message;
    }

    open fun getStatus(): HttpStatus {
        return this.status;
    }

    open fun getCode(): Int {
        return this.code;
    }
}