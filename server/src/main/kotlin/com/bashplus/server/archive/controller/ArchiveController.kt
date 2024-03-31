package com.bashplus.server.archive.controller

import com.bashplus.server.archive.dto.ArchiveVideoRequestDTO
import com.bashplus.server.archive.service.ArchiveService
import com.bashplus.server.common.ResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@Tag(name = "아카이브 API", description = "영상 기록 저장 관련 API - 나중에보기, 좋아요")
@RestController
@RequestMapping("/archive")
class ArchiveController {

    @Autowired
    private lateinit var archiveService: ArchiveService

    @Operation(summary = "영상 시청 기록 목록 조회 API", description = "")
    @GetMapping("/videos")
    fun getWatchedVideos(request: HttpServletRequest, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseDTO {
        val userId = request.getAttribute("userId").toString().toLong()
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = archiveService.getVideoWatchRecords(userId, pageable)
        return ResponseDTO(result)
    }

    @Operation(summary = "나중에 볼 영상 등록 API", description = "")
    @PatchMapping("/last/video")
    fun addVideo(@RequestBody archiveVideoRequestDTO: ArchiveVideoRequestDTO, request: HttpServletRequest): ResponseDTO {
        val userId = request.getAttribute("userId").toString().toLong()
        archiveVideoRequestDTO.uid = userId
        archiveService.updateArchiveLastVideo(archiveVideoRequestDTO)
        return ResponseDTO(HttpStatus.CREATED.reasonPhrase)
    }

    @Operation(summary = "나중에 볼 영상 목록 조회 API", description = "")
    @GetMapping("/last/videos")
    fun getLikedVideos(request: HttpServletRequest, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseDTO {
        val userId = request.getAttribute("userId").toString().toLong()
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = archiveService.getLastVideos(userId, pageable)
        return ResponseDTO(result)
    }


    @Operation(summary = "타임스탬프 등록 API", description = "")
    @PostMapping("/time")
    fun stampCurrentTime(@RequestBody archiveVideoRequestDTO: ArchiveVideoRequestDTO, request: HttpServletRequest): ResponseDTO {
        val userId = request.getAttribute("userId").toString().toLong()
        archiveVideoRequestDTO.uid = userId
        return ResponseDTO(HttpStatus.CREATED.reasonPhrase)
    }

    @Operation(summary = "좋아요한 영상 목록 조회 API", description = "")
    @GetMapping("/like/videos")
    fun getArchivedVideos(request: HttpServletRequest, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseDTO {
        val userId = request.getAttribute("userId").toString().toLong()
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = archiveService.getLikeVideos(userId, pageable)
        return ResponseDTO(result)
    }

    @Operation(summary = "비디오 좋아요 등록 API", description = "")
    @PatchMapping("/like/video")
    fun updateLikeOnVideo(@RequestBody archiveVideoRequestDTO: ArchiveVideoRequestDTO, request: HttpServletRequest): ResponseDTO {
        val userId = request.getAttribute("userId").toString().toLong()
        archiveVideoRequestDTO.uid = userId
        archiveService.updateArchiveLikeVideo(archiveVideoRequestDTO)
        return ResponseDTO(HttpStatus.CREATED.reasonPhrase)
    }

}