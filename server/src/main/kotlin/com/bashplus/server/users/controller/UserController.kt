package com.bashplus.server.users.controller

import com.bashplus.server.common.ResponseDTO
import com.bashplus.server.common.exception.ApiException
import com.bashplus.server.common.exception.ExceptionEnum
import com.bashplus.server.users.dto.InterestRequestDTO
import com.bashplus.server.users.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.jetbrains.annotations.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "회원 세팅 API", description = "회원 세팅 - 마이 페이지 관련 API, 조회 및 업데이트")
@RestController
@RequestMapping("/user")
class UserController {


    @Autowired
    private lateinit var userService: UserService

    @Operation(summary = "관심분야 설정 API", description = "")
    @PostMapping("/interesting")
    fun setInterestingCategory(@NotNull @RequestBody interestRequest: Map<String, ArrayList<InterestRequestDTO>>, request: HttpServletRequest): ResponseDTO {
        val userId = request.getAttribute("userId").toString().toLong()
        val paramList: ArrayList<InterestRequestDTO>? = interestRequest["param"]
        if (!paramList.isNullOrEmpty()) {
            userService.setInterestingCategory(userId, paramList)
            return ResponseDTO(HttpStatus.OK.reasonPhrase)
        } else {
            throw ApiException(ExceptionEnum.BAD_REQUEST)
        }
    }

    @Operation(summary = "댓글 기록 조회 API", description = "")
    @GetMapping("/comments")
    fun getComments(request: HttpServletRequest, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseDTO {
        val userId = request.getAttribute("userId").toString().toLong()
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val commentLists = userService.getComments(userId, pageable)
        return ResponseDTO(commentLists)
    }
}