package com.bashplus.server.users.controller

import com.bashplus.server.common.ResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "회원 세팅 API", description = "회원 세팅 - 마이 페이지 관련 API, 조회 및 업데이트")
@RestController
@RequestMapping("/users-info")
class UsersInfoController {
    @Operation(summary = "ID 찾기 API", description = "")
    @GetMapping("/restore/id")
    fun findId(): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "비밀번호 찾기 API", description = "")
    @GetMapping("/restore/password")
    fun findPassword(): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "비밀번호 변경 API", description = "")
    @PostMapping("/reset/password")
    fun resetPassword(): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "관심분야 설정 API", description = "")
    @PostMapping("/{userId}/interesting-category")
    fun setInterestingCategory(): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "관심분야 레벨 설정 API", description = "")
    @PostMapping("/{userId}/level")
    fun setLevel(): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "댓글 기록 조회 API", description = "")
    @GetMapping("/{userId}/comments")
    fun getComments(): ResponseDTO {
        return ResponseDTO()
    }
}