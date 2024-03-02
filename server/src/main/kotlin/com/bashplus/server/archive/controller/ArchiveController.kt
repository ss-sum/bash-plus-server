package com.bashplus.server.archive.controller

import com.bashplus.server.archive.dto.ArchiveVideoRequestDTO
import com.bashplus.server.archive.service.ArchiveService
import com.bashplus.server.common.ResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@Tag(name = "아카이브 API", description = "영상 기록 저장 관련 API - 나중에보기, 좋아요")
@RestController
@RequestMapping("/archive")
class ArchiveController {

    @Autowired
    private lateinit var archiveService: ArchiveService

    @Operation(summary = "영상 시청 기록 목록 조회 API", description = "")
    @GetMapping("/videos/usr/{userId}")
    fun getWatchedVideos(@PathVariable userId: Long): ResponseDTO {
        val result = archiveService.getVideoWatchRecords(userId)
        return ResponseDTO(result)
    }

    @Operation(summary = "나중에 볼 영상 등록 API", description = "")
    @PostMapping("/last/video")
    fun addVideo(@RequestBody archiveVideoRequestDTO: ArchiveVideoRequestDTO): ResponseDTO {
        archiveService.registerArchiveLastVideo(archiveVideoRequestDTO)
        return ResponseDTO()
    }

    @Operation(summary = "나중에 볼 영상 목록 조회 API", description = "")
    @GetMapping("/last/videos/user/{userId}")
    fun getLikedVideos(@PathVariable userId: Long): ResponseDTO {
        val result = archiveService.getLastVideos(userId)
        return ResponseDTO(result)
    }


    @Operation(summary = "타임스탬프 등록 API", description = "")
    @PostMapping("/time/video/{videoId}/user/{userId}")
    fun stampCurrentTime(@PathVariable videoId: String): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "좋아요한 영상 목록 조회 API", description = "")
    @GetMapping("/like/videos/user/{userId}")
    fun getArchivedVideos(@PathVariable userId: Long): ResponseDTO {
        val result = archiveService.getLikeVideos(userId)
        return ResponseDTO(result)
    }

    @Operation(summary = "비디오 좋아요 등록 API", description = "")
    @PostMapping("/like/video")
    fun stampLikeOnVideo(@RequestBody archiveVideoRequestDTO: ArchiveVideoRequestDTO): ResponseDTO {
        archiveService.registerArchiveLikeVideo(archiveVideoRequestDTO)
        return ResponseDTO()
    }

}