package com.bashplus.server.common

import com.bashplus.server.common.exception.ExceptionEnum
import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.MediaType
import org.springframework.security.oauth2.jwt.JwtException
import org.springframework.security.oauth2.jwt.JwtValidationException
import org.springframework.web.client.HttpClientErrorException.BadRequest
import org.springframework.web.filter.OncePerRequestFilter
import java.io.IOException

class CustomExceptionHandlerFilter : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            filterChain.doFilter(request, response);
        } catch (e: BadRequest) {
            setErrorResponse(response, ExceptionEnum.BAD_REQUEST)
        } catch (e: JwtValidationException) {
            setErrorResponse(response, ExceptionEnum.EXPIRED_TOKEN)
        } catch (e: JwtException) {
            setErrorResponse(response, ExceptionEnum.TOKEN_ERROR)
        } catch (e: Exception) {
            setErrorResponse(response, ExceptionEnum.INTERNAL_SERVER_ERROR)
        }
    }

    private fun setErrorResponse(response: HttpServletResponse, e: ExceptionEnum) {
        var objectMapper: ObjectMapper = ObjectMapper()
        response.status = e.getStatus().value()
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        var errorResponse: ErrorResponse = ErrorResponse(e.getCode(), e.getMessage()!!)
        try {
            response.writer.write(objectMapper.writeValueAsString(errorResponse))
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    data class ErrorResponse(val code: Int, val message: String) {
    }
}