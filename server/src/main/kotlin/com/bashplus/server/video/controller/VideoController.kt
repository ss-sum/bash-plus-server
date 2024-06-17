package com.bashplus.server.video.controller

import com.bashplus.server.common.ResponseDTO
import com.bashplus.server.common.ResponseListDTO
import com.bashplus.server.common.SortingEnum
import com.bashplus.server.common.exception.ApiExceptionEntity
import com.bashplus.server.video.dto.*
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
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            ApiResponse(
                responseCode = "400",
                description = "BAD REQUEST",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "INTERNAL SERVER ERROR",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            )
        ]
    )
    @GetMapping
    fun getAllVideo(
        @RequestParam pageSize: Int,
        @RequestParam pageNum: Int,
        @RequestParam(defaultValue = "DATE") order: VideoOrderEnum,
        @RequestParam(defaultValue = "DESC") sort: SortingEnum
    ): ResponseListDTO<VideoDTO> {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = videoService.getAllVideos(order, sort, pageable)
        return result
    }

    @Operation(summary = "영상 정보 API", description = "컨퍼런스 영상의 정보를 보여주는 API")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            ApiResponse(
                responseCode = "400",
                description = "BAD REQUEST",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "INTERNAL SERVER ERROR",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            )
        ]
    )
    @GetMapping("/{videoId}")
    fun getVideo(@PathVariable videoId: Long): ResponseDTO<VideoDTO> {
        val result = videoService.getVideoInfo(videoId)
        return ResponseDTO(result)
    }

    @Operation(summary = "영상 댓글 조회 API", description = "영상에 달린 댓글을 볼 수 있게 해주는 API")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            ApiResponse(
                responseCode = "400",
                description = "BAD REQUEST",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "INTERNAL SERVER ERROR",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            )
        ]
    )
    @GetMapping("/{videoId}/comments")
    fun getVideoComments(
        @PathVariable videoId: Long,
        @RequestParam pageSize: Int,
        @RequestParam pageNum: Int
    ): ResponseListDTO<CommentDTO> {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = videoService.getVideoCommentInfo(videoId, pageable)
        return result
    }

    @Operation(summary = "영상 댓글 등록 API", description = "영상에 본인의 댓글을 등록하게 해주는 API")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            ApiResponse(
                responseCode = "400",
                description = "BAD REQUEST",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "INTERNAL SERVER ERROR",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            )
        ]
    )
    @PostMapping("/comment")
    fun writeComment(@NotNull @RequestBody commentRequest: CommentRequestDTO): ResponseDTO<String> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as User).username.toLong()
        commentRequest.uid = userId
        videoService.writeComment(commentRequest)
        return ResponseDTO(HttpStatus.OK.reasonPhrase)
    }

    @Operation(summary = "영상 댓글 수정 API", description = "영상에 본인의 댓글을 수정하게 해주는 API")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            ApiResponse(
                responseCode = "400",
                description = "BAD REQUEST",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "INTERNAL SERVER ERROR",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            )
        ]
    )
    @PutMapping("/comment")
    fun updateComment(
        @PathVariable("commentId") commentId: Long,
        @NotNull @RequestBody commentRequest: CommentRequestDTO
    ): ResponseDTO<String> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as User).username.toLong()
        commentRequest.uid = userId
        videoService.updateComment(commentRequest)
        return ResponseDTO(HttpStatus.OK.reasonPhrase)
    }

    @Operation(summary = "영상 댓글 삭제 API", description = "영상에 본인의 댓글을 삭제하게 해주는 API")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            ApiResponse(
                responseCode = "400",
                description = "BAD REQUEST",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "INTERNAL SERVER ERROR",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            )
        ]
    )
    @DeleteMapping("/comment/{commentId}")
    fun deleteComment(@PathVariable("commentId") commentId: Long): ResponseDTO<String> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as User).username.toLong()
        videoService.deleteComment(commentId)
        return ResponseDTO(HttpStatus.OK.reasonPhrase)
    }

    @Operation(summary = "영상 댓글 좋아요 API", description = "영상 댓글에 좋아요를 기록하는 API. 기존에 좋아요 기록이 있으면 좋아요 해제, 없으면 좋아요 기록 추가.")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            ApiResponse(
                responseCode = "400",
                description = "BAD REQUEST",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "INTERNAL SERVER ERROR",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            )
        ]
    )
    @PostMapping("/comment/like")
    fun likeComment(@NotNull @RequestBody commentRequest: CommentRequestDTO): ResponseDTO<String> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as User).username.toLong()
        commentRequest.uid = userId
        videoService.likeComment(commentRequest)
        return ResponseDTO(HttpStatus.OK.reasonPhrase)
    }

    @Operation(summary = "영상 시청 기록 API", description = "영상 시청 시작, 종료 등 기록을 위한 API")
    @ApiResponses(
        value = [
            ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
            ApiResponse(
                responseCode = "400",
                description = "BAD REQUEST",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            ),
            ApiResponse(
                responseCode = "500",
                description = "INTERNAL SERVER ERROR",
                content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]
            )
        ]
    )
    @PostMapping("/watch")
    fun watchVideo(@NotNull @RequestBody watchRequest: WatchRequestDTO): ResponseDTO<String> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as User).username.toLong()
        watchRequest.uid = userId
        videoService.updateWatchRecord(watchRequest)
        return ResponseDTO(HttpStatus.OK.reasonPhrase)
    }
}