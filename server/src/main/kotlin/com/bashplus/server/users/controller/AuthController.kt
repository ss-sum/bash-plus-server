package com.bashplus.server.users.controller

import com.bashplus.server.common.ResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "회원 인증 API", description = "회원 관련 기본 API - 회원가입, 로그인 관련")
@RestController
@RequestMapping("/auth")
class AuthController {
    @Operation(summary = "회원가입 API", description = "")
    @PostMapping("/join")
    fun join(): ResponseDTO {
        return ResponseDTO()
    }


    @Operation(summary = "소셜 로그인 API", description = "각 플랫폼의 인증을 통해서 로그인")
    @GetMapping("/login/social/platform/{platform}")
    fun socialLogin(@PathVariable platform: String): ResponseDTO {
        return ResponseDTO()
    }


    @Operation(summary = "소셜 로그아웃 API", description = "")
    @PostMapping("/logout")
    fun socialLogout(): ResponseDTO {
        return ResponseDTO()
    }


}