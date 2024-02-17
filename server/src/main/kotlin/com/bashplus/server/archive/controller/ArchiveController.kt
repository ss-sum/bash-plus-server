package com.bashplus.server.archive.controller

import com.bashplus.server.common.ResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "아카이브 API", description = "영상 기록 저장 관련 API - 나중에보기, 좋아요")
@RestController
@RequestMapping("/archive")
class ArchiveController {

    @Operation(summary = "영상 시청 기록 목록 조회 API", description = "")
    @GetMapping("/videos")
    fun getWatchedVideos(): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "나중에 볼 영상 등록 API", description = "")
    @PostMapping("/last/video/{videoId}")
    fun addVideo(@PathVariable videoId: String): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "나중에 볼 영상 목록 조회 API", description = "")
    @GetMapping("/last/videos")
    fun getLastVideos(): ResponseDTO {
        return ResponseDTO()
    }


    @Operation(summary = "타임스탬프 등록 API", description = "")
    @PostMapping("/time/video/{videoId}")
    fun stampCurrentTime(@PathVariable videoId: String): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "좋아요한 영상 목록 조회 API", description = "")
    @GetMapping("/like/videos")
    fun getArchivedVideos(): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "비디오 좋아요 등록 API", description = "")
    @PostMapping("/like/video/{videoId}")
    fun stampLikeOnVideo(@PathVariable videoId: String): ResponseDTO {
        return ResponseDTO()
    }

}