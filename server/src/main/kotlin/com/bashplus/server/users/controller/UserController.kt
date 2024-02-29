package com.bashplus.server.users.controller

import com.bashplus.server.common.ResponseDTO
import com.bashplus.server.users.dto.InterestRequestDTO
import com.bashplus.server.users.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.jetbrains.annotations.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Tag(name = "회원 세팅 API", description = "회원 세팅 - 마이 페이지 관련 API, 조회 및 업데이트")
@RestController
@RequestMapping("/user")
class UserController {


    @Autowired
    private lateinit var userService: UserService

    @Operation(summary = "관심분야 설정 API", description = "")
    @PostMapping("/{userId}/interesting")
    fun setInterestingCategory(@PathVariable userId: Int, @NotNull @RequestBody interestRequest: Map<String, ArrayList<InterestRequestDTO>>): ResponseDTO {
        val paramList: ArrayList<InterestRequestDTO>? = interestRequest["param"]
        if (!paramList.isNullOrEmpty()) {
            userService.setInterestingCategory(userId, paramList)
            return ResponseDTO()
        } else {
            // 예외 처리 또는 에러 응답을 수행하는 부분 추가
            return ResponseDTO()
        }
    }

    @Operation(summary = "댓글 기록 조회 API", description = "")
    @GetMapping("/{userId}/comments")
    fun getComments(@PathVariable userId: String): ResponseDTO {
        return ResponseDTO()
    }
}