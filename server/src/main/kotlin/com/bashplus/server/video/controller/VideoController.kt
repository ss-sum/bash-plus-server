package com.bashplus.server.video.controller

import com.bashplus.server.common.ResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "비디오 API", description = "컨퍼런스 영상 관련 API")
@RestController
@RequestMapping("/video")
class VideoController {

    @Operation(summary = "영상 정보 API", description = "")
    @GetMapping("/{videoId}")
    fun getVideo(@PathVariable videoId: String): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "영상 댓글 조회 API", description = "")
    @GetMapping("/{videoId}/comments")
    fun getVideoComments(@PathVariable videoId: String): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "영상 댓글 등록 API", description = "")
    @PostMapping("/{videoId}/comment")
    fun writeComment(@PathVariable videoId: String): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "영상 시청 기록 API", description = "영상 시청 시작, 종료 등 기록을 위한 API")
    @PostMapping("/{videoId}/watch")
    fun watchVideo(@PathVariable videoId: String): ResponseDTO {
        return ResponseDTO()
    }
}