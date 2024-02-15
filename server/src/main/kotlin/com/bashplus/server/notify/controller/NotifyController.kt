package com.bashplus.server.notify.controller

import com.bashplus.server.common.ResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


@Tag(name = "알람 API", description = "알람 관련 API")
@RestController
@RequestMapping("/notify")
class NotifyController {
    @Operation(summary = "알림 설정 API", description = "알림 받기/끄기 등의 기본 설정")
    @PostMapping("/setting")
    fun addNotifySetting(): ResponseDTO {
        return ResponseDTO()
    }
    @Operation(summary = "알림 구독 API", description = "특정 주제 구독 등의 설정")
    @PostMapping("/subscribe")
    fun subscribe(): ResponseDTO{
        return ResponseDTO()
    }
}