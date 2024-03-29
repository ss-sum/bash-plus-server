package com.bashplus.server.common.exception

import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionAdvice {
    @ExceptionHandler(ApiException::class)
    open fun exceptionHandler(request: HttpServletRequest, e: ApiException): ResponseEntity<ApiExceptionEntity> {
        return ResponseEntity
                .status(e.getError().getStatus())
                .body(ApiExceptionEntity(e.getError().getCode(), e.getError().getMessage()!!))
    }

    @ExceptionHandler(RuntimeException::class)
    open fun exceptionHandler(request: HttpServletRequest, e: RuntimeException): ResponseEntity<ApiExceptionEntity> {
        return ResponseEntity
                .status(ExceptionEnum.RUNTIME_EXCEPTION.getStatus())
                .body(ApiExceptionEntity(ExceptionEnum.RUNTIME_EXCEPTION.getCode(), e.message!!))
    }

    @ExceptionHandler(AccessDeniedException::class)
    open fun exceptionHandler(request: HttpServletRequest, e: AccessDeniedException): ResponseEntity<ApiExceptionEntity> {
        return ResponseEntity
                .status(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getStatus())
                .body(ApiExceptionEntity(ExceptionEnum.ACCESS_DENIED_EXCEPTION.getCode(), e.message!!))
    }

    @ExceptionHandler(Exception::class)
    open fun exceptionHandler(request: HttpServletRequest, e: Exception): ResponseEntity<ApiExceptionEntity> {
        return ResponseEntity
                .status(ExceptionEnum.INTERNAL_SERVER_ERROR.getStatus())
                .body(ApiExceptionEntity(ExceptionEnum.INTERNAL_SERVER_ERROR.getCode(), e.message!!))
    }
}