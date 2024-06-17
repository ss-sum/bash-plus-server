package com.bashplus.server.common.exception

import org.springframework.http.HttpStatus

enum class ExceptionEnum(
    private val status: HttpStatus,
    private val code: Int,
    private var message: String? = null
) {
    BAD_REQUEST(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
    REQUEST_TYPE_BAD_REQUEST_PARAM(
        HttpStatus.BAD_REQUEST,
        400,
        "Request Type Is Wrong, Add 'param' Key In Request Body"
    ),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 500, "Internal Server Error"),
    ACCESS_DENIED_EXCEPTION(HttpStatus.UNAUTHORIZED, 403, "Unauthorized"),
    RUNTIME_EXCEPTION(HttpStatus.BAD_REQUEST, 400, "Bad Request"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, 401, "Expired Token"),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 401, "Invalid Token"),
    BAD_TOKEN_FORM(HttpStatus.BAD_REQUEST, 400, "Token Format Is Invalid Or Token Does Not Exist"),
    TOKEN_ERROR(HttpStatus.BAD_REQUEST, 400, "Token Error"),
    NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Not Found"),
    VIDEO_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Video Not Found"),
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Category Not Found"),
    COMMENT_NOT_FOUND(HttpStatus.NOT_FOUND, 404, "Comment Not Found");

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