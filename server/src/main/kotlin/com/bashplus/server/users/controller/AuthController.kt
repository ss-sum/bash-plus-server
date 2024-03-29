package com.bashplus.server.users.controller

import com.bashplus.server.common.ResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Tag(name = "회원 인증 API", description = "회원 관련 기본 API - 회원가입, 로그인 관련")
@RestController
@RequestMapping("/auth")
class AuthController {
    @Operation(summary = "소셜 로그인 API", description = "각 플랫폼의 인증을 통해서 로그인")
    @GetMapping("/login/social/platform/{platform}")
    fun socialLogin(@PathVariable platform: String, request: HttpServletRequest, response: HttpServletResponse): Unit {
    }

    @Operation(summary = "소셜 로그인 API", description = "로그인 결과 반환")
    @GetMapping("/authorization/{platform}")
    fun authorization(@PathVariable platform: String): ResponseEntity<ResponseDTO> {
        return ResponseEntity.ok(ResponseDTO(HttpStatus.OK.reasonPhrase))
    }


    @Operation(summary = "소셜 로그아웃 API", description = "")
    @PostMapping("/logout")
    fun socialLogout(): ResponseDTO {
        return ResponseDTO()
    }


}