package com.bashplus.server.archive.controller

import com.bashplus.server.archive.dto.ArchiveVideoDTO
import com.bashplus.server.archive.dto.ArchiveVideoRequestDTO
import com.bashplus.server.archive.dto.ArchiveVideoWatchRecordDTO
import com.bashplus.server.archive.service.ArchiveService
import com.bashplus.server.common.ResponseDTO
import com.bashplus.server.common.ResponseListDTO
import com.bashplus.server.common.exception.ApiExceptionEntity
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@Tag(name = "아카이브 API", description = "영상 기록 저장 관련 API - 나중에보기, 좋아요")
@RestController
@RequestMapping("/archive")
class ArchiveController {

    @Autowired
    private lateinit var archiveService: ArchiveService

    @Operation(summary = "영상 시청 기록 목록 조회 API", description = "마이페이지에서 유저가 시청한 영상들을 목록으로 조회할 수 있도록 하는 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @GetMapping("/videos")
    fun getWatchedVideos(@RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO<ArchiveVideoWatchRecordDTO> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as User).username.toLong()
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = archiveService.getVideoWatchRecords(userId, pageable)
        return result
    }

    @Operation(summary = "나중에 볼 영상 등록 API", description = "해당 영상을 유저의 '나중에 볼 영상' 목록에 추가하는 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
        ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @PatchMapping("/last/video")
    fun addVideo(@RequestBody archiveVideoRequestDTO: ArchiveVideoRequestDTO): ResponseDTO<String> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as User).username.toLong()
        archiveVideoRequestDTO.uid = userId
        archiveService.updateArchiveLastVideo(archiveVideoRequestDTO)
        return ResponseDTO(HttpStatus.CREATED.reasonPhrase)
    }

    @Operation(summary = "나중에 볼 영상 목록 조회 API", description = "유저가 등록한 '나중에 볼 영상' 목록을 조회하는 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @GetMapping("/last/videos")
    fun getLikedVideos(@RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO<ArchiveVideoDTO> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as User).username.toLong()
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = archiveService.getLastVideos(userId, pageable)
        return result
    }


    @Operation(summary = "타임스탬프 등록 API", description = "")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
        ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @PostMapping("/timestamp")
    fun stampCurrentTime(@RequestBody archiveVideoRequestDTO: ArchiveVideoRequestDTO): ResponseDTO<String> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as User).username.toLong()
        archiveVideoRequestDTO.uid = userId
        return ResponseDTO(HttpStatus.CREATED.reasonPhrase)
    }

    @Operation(summary = "좋아요한 영상 목록 조회 API", description = "")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @GetMapping("/like/videos")
    fun getArchivedVideos(@RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO<ArchiveVideoDTO> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as User).username.toLong()
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = archiveService.getLikeVideos(userId, pageable)
        return result
    }

    @Operation(summary = "비디오 좋아요 등록 API", description = "")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
        ApiResponse(responseCode = "401", description = "UNAUTHORIZED", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @PatchMapping("/like/video")
    fun updateLikeOnVideo(@RequestBody archiveVideoRequestDTO: ArchiveVideoRequestDTO): ResponseDTO<String> {
        val userId = (SecurityContextHolder.getContext().authentication.principal as User).username.toLong()
        archiveVideoRequestDTO.uid = userId
        archiveService.updateArchiveLikeVideo(archiveVideoRequestDTO)
        return ResponseDTO(HttpStatus.CREATED.reasonPhrase)
    }

}