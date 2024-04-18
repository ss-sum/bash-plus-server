package com.bashplus.server.users.controller

import com.bashplus.server.common.ResponseDTO
import com.bashplus.server.common.exception.ApiExceptionEntity
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "회원 인증 API", description = "회원 관련 기본 API - 회원가입, 로그인 관련")
@RestController
@RequestMapping("/auth")
class AuthController {
    @Operation(summary = "소셜 로그인 API", description = "각 플랫폼의 인증을 통해서 로그인")
    @ApiResponses(value = [
        ApiResponse(responseCode = "301", description = "Permanently Moved "),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @GetMapping("/login/social/platform/{platform}")
    fun socialLogin(@PathVariable platform: String, request: HttpServletRequest, response: HttpServletResponse): Unit {
    }

    @Hidden
    @Operation(summary = "소셜 로그인 API", description = "로그인 결과 반환")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @GetMapping("/authorization/{platform}")
    fun authorization(@PathVariable platform: String): ResponseDTO<String> {
        return ResponseDTO(HttpStatus.OK.reasonPhrase)
    }


    @Operation(summary = "소셜 로그아웃 API", description = "로그아웃 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
        ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    ])
    @PostMapping("/logout")
    fun socialLogout(request: HttpServletRequest): ResponseDTO<String> {
        var cookie: Array<out Cookie>? = request.getCookies()
        if (cookie != null) {
            for (c in cookie) {
                if (c.name.equals("Authorization")) {
                    c.maxAge = 0
                }
            }
        }
        return ResponseDTO(HttpStatus.OK.reasonPhrase)
    }


}