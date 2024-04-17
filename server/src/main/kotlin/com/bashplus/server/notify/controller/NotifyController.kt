package com.bashplus.server.notify.controller

import com.bashplus.server.common.ResponseDTO
import com.bashplus.server.common.exception.ApiExceptionEntity
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "알람 API", description = "알람 관련 API")
@RestController
@RequestMapping("/notify")
class NotifyController {
    @Hidden
    @Operation(summary = "알림 설정 API", description = "알림 받기/끄기 등의 기본 설정")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @PostMapping("/setting")
    fun addNotifySetting(): ResponseDTO<String> {
        return ResponseDTO("")
    }

    @Hidden
    @Operation(summary = "알림 구독 API", description = "특정 주제 구독 등의 설정")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @PostMapping("/subscribe")
    fun subscribe(): ResponseDTO<String> {
        return ResponseDTO("")
    }
}