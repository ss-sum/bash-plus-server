package com.bashplus.server.users.controller

import com.bashplus.server.common.ResponseDTO
import com.bashplus.server.common.ResponseListDTO
import com.bashplus.server.common.exception.ApiException
import com.bashplus.server.common.exception.ApiExceptionEntity
import com.bashplus.server.common.exception.ExceptionEnum
import com.bashplus.server.information.dto.CategoryInformationDTO
import com.bashplus.server.users.dto.InterestRequestDTO
import com.bashplus.server.users.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.ExampleObject
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.jetbrains.annotations.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@Tag(name = "회원 세팅 API", description = "회원 세팅 - 마이 페이지 관련 API, 조회 및 업데이트")
@RestController
@RequestMapping("/user")
class UserController {


    @Autowired
    private lateinit var userService: UserService

    @Operation(summary = "관심분야 설정 API", description = "본인의 관심분야를 설정하게 해주는 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = InterestRequestDTO::class))]),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @PostMapping("/interesting")
    @io.swagger.v3.oas.annotations.parameters.RequestBody(content = [Content(examples = [ExampleObject(value = """{"param":["category":"spring","level":1]}""")])])
    fun setInterestingCategory(@NotNull @RequestBody interestRequest: Map<String, ArrayList<InterestRequestDTO>>): ResponseDTO {
        val userId = (SecurityContextHolder.getContext().authentication.principal as User).username.toLong()
        val paramList: ArrayList<InterestRequestDTO>? = interestRequest["param"]
        if (!paramList.isNullOrEmpty()) {
            userService.setInterestingCategory(userId, paramList)
            return ResponseDTO(HttpStatus.OK.reasonPhrase)
        } else {
            throw ApiException(ExceptionEnum.REQUEST_TYPE_BAD_REQUEST_PARAM)
        }
    }

    @Operation(summary = "댓글 기록 조회 API", description = "본인이 쓴 댓글을 조회할 수 있도록 해주는 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @GetMapping("/comments")
    fun getComments(@RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO {
        val userId = (SecurityContextHolder.getContext().authentication.principal as User).username.toLong()
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = userService.getComments(userId, pageable)
        return result
    }
}