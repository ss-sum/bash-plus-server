package com.bashplus.server.users.controller

import com.bashplus.server.common.ResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "회원 세팅 API", description = "회원 세팅 - 마이 페이지 관련 API, 조회 및 업데이트")
@RestController
@RequestMapping("/user")
class UserController {
    @Operation(summary = "관심분야 설정 API", description = "")
    @PostMapping("/{userId}/interesting")
    fun setInterestingCategory(@PathVariable userId: String): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "댓글 기록 조회 API", description = "")
    @GetMapping("/{userId}/comments")
    fun getComments(@PathVariable userId: String): ResponseDTO {
        return ResponseDTO()
    }
}