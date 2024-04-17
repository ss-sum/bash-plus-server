package com.bashplus.server.video.controller

import com.bashplus.server.common.ResponseDTO
import com.bashplus.server.common.ResponseListDTO
import com.bashplus.server.common.exception.ApiExceptionEntity
import com.bashplus.server.video.dto.CommentDTO
import com.bashplus.server.video.dto.CommentRequestDTO
import com.bashplus.server.video.dto.VideoDTO
import com.bashplus.server.video.dto.WatchRequestDTO
import com.bashplus.server.video.service.VideoService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
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

@Tag(name = "비디오 API", description = "컨퍼런스 영상 관련 API")
@RestController
@RequestMapping("/video")
class VideoController {

    @Autowired
    private lateinit var videoService: VideoService

    @Operation(summary = "영상 전체 목록 API", description = "등록된 전체 영상을 목록으로 조회하게 해주는 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = VideoDTO::class))]),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @GetMapping("/")
    fun getAllVideo(@PathVariable videoId: Long, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO<VideoDTO> {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = videoService.getAllVideos(pageable)
        return result
    }

    @Operation(summary = "영상 정보 API", description = "컨퍼런스 영상의 정보를 보여주는 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = VideoDTO::class))]),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @GetMapping("/{videoId}")
    fun getVideo(@PathVariable videoId: Long): ResponseDTO {
        val result = videoService.getVideoInfo(videoId)
        return ResponseDTO(result)
    }

    @Operation(summary = "영상 댓글 조회 API", description = "영상에 달린 댓글을 볼 수 있게 해주는 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = CommentDTO::class))]),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @GetMapping("/{videoId}/comments")
    fun getVideoComments(@PathVariable videoId: Long, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO<CommentDTO> {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = videoService.getVideoCommentInfo(videoId, pageable)
        return result
    }

    @Operation(summary = "영상 댓글 등록 API", description = "영상에 본인의 댓글을 등록하게 해주는 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = CommentRequestDTO::class))]),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @PostMapping("/comment")
    fun writeComment(@NotNull @RequestBody commentRequest: CommentRequestDTO): ResponseDTO {
        val userId = (SecurityContextHolder.getContext().authentication.principal as User).username.toLong()
        commentRequest.uid = userId
        videoService.writeComment(commentRequest)
        return ResponseDTO(HttpStatus.OK.reasonPhrase)
    }

    @Operation(summary = "영상 시청 기록 API", description = "영상 시청 시작, 종료 등 기록을 위한 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = WatchRequestDTO::class))]),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @PostMapping("/watch")
    fun watchVideo(@NotNull @RequestBody watchRequest: WatchRequestDTO): ResponseDTO {
        val userId = (SecurityContextHolder.getContext().authentication.principal as User).username.toLong()
        watchRequest.uid = userId
        videoService.updateWatchRecord(watchRequest)
        return ResponseDTO(HttpStatus.OK.reasonPhrase)
    }
}