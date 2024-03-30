package com.bashplus.server.video.controller

import com.bashplus.server.common.ResponseDTO
import com.bashplus.server.video.dto.CommentRequestDTO
import com.bashplus.server.video.dto.WatchRequestDTO
import com.bashplus.server.video.service.VideoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.jetbrains.annotations.NotNull
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "비디오 API", description = "컨퍼런스 영상 관련 API")
@RestController
@RequestMapping("/video")
class VideoController {

    @Autowired
    private lateinit var videoService: VideoService

    @Operation(summary = "영상 전체 목록 API", description = "")
    @GetMapping("/")
    fun getAllVideo(@PathVariable videoId: Long, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseDTO {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = videoService.getAllVideos(pageable)
        return ResponseDTO(result)
    }

    @Operation(summary = "영상 정보 API", description = "")
    @GetMapping("/{videoId}")
    fun getVideo(@PathVariable videoId: Long): ResponseDTO {
        val result = videoService.getVideoInfo(videoId)
        return ResponseDTO(result)
    }

    @Operation(summary = "영상 댓글 조회 API", description = "")
    @GetMapping("/{videoId}/comments")
    fun getVideoComments(@PathVariable videoId: Long, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseDTO {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = videoService.getVideoCommentInfo(videoId, pageable)
        return ResponseDTO(result)
    }

    @Operation(summary = "영상 댓글 등록 API", description = "")
    @PostMapping("/comment")
    fun writeComment(@NotNull @RequestBody commentRequest: CommentRequestDTO, request: HttpServletRequest): ResponseDTO {
        val userId = request.getAttribute("userId").toString().toLong()
        commentRequest.uid = userId
        videoService.writeComment(commentRequest)
        return ResponseDTO(HttpStatus.OK.reasonPhrase)
    }

    @Operation(summary = "영상 시청 기록 API", description = "영상 시청 시작, 종료 등 기록을 위한 API")
    @PostMapping("/watch")
    fun watchVideo(@NotNull @RequestBody watchRequest: WatchRequestDTO, request: HttpServletRequest): ResponseDTO {
        val userId = request.getAttribute("userId").toString().toLong()
        watchRequest.uid = userId
        videoService.updateWatchRecord(watchRequest)
        return ResponseDTO(HttpStatus.OK.reasonPhrase)
    }
}