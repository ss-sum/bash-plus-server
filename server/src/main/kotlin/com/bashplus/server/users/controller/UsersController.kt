package com.bashplus.server.users.controller

import com.bashplus.server.common.ResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 API", description = "회원 관련 기본 API - 회원가입, 로그인 관련")
@RestController
@RequestMapping("/users")
class UsersController {
    @Operation(summary = "회원가입 API", description = "")
    @PostMapping("/join")
    fun join(): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "일반 로그인 API", description = "아이디, 비밀번호 입력해서 로그인")
    @PostMapping("/login/basic")
    fun login(): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "소셜 로그인 API", description = "각 플랫폼의 인증을 통해서 로그인")
    @PostMapping("/login/social/{platform}")
    fun socialLogin(@PathVariable platform: String): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "일반 로그아웃 API", description = "")
    @PostMapping("/logout/basic")
    fun logout(): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "소셜 로그아웃 API", description = "")
    @PostMapping("/logout/social")
    fun socialLogout(): ResponseDTO {
        return ResponseDTO()
    }


}