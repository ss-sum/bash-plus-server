package com.bashplus.server.conference.controller

import com.bashplus.server.common.ResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "컨퍼런스 API", description = "컨퍼런스 영상 관련 API")
@RestController
@RequestMapping("/conference")
class ConferenceController {
    @Operation(summary = "영상 정보 API", description = "")
    @GetMapping("/video/{videoId}")
    fun getVideo(@PathVariable videoId: String): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "영상 댓글 조회 API", description = "")
    @GetMapping("/video/{videoId}/comments")
    fun getVideoComments(@PathVariable videoId: String): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "영상 댓글 등록 API", description = "")
    @PostMapping("/video/{videoId}/comment")
    fun writeComment(@PathVariable videoId: String): ResponseDTO {
        return ResponseDTO()
    }

}