package com.bashplus.server.conference.controller

import com.bashplus.server.common.ResponseDTO
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.*

@Tag(name = "컨퍼런스 정보 API", description = "컨퍼런스 관련 API - 조회, 검색 등")
@RestController
@RequestMapping("/conference-info")
class ConferenceInfoController {
    @Operation(summary = "전체 영상 목록 조회 API", description = "")
    @GetMapping("/search/video/whole")
    fun getWholeVideos(): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "컨퍼런스 주최 목록 API", description = "검색 시 선택지 제공을 위한 목록 API")
    @GetMapping("/hosts")
    fun getHosts(): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "컨퍼런스 주최 검색 API", description = "")
    @GetMapping("/search/host/{keyword}")
    fun searchHost(@PathVariable keyword: String): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "컨퍼런스 카테고리 목록 API", description = "검색 시 선택지 제공을 위한 목록 API")
    @GetMapping("/category")
    fun getCategory(): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "컨퍼런스 카테고리 검색 API", description = "")
    @PostMapping("/search/category/{keyword}")
    fun searchCategory(@PathVariable keyword: String): ResponseDTO {
        return ResponseDTO()
    }

    @Operation(summary = "컨퍼런스 비디오 제목 검색 API", description = "")
    @PostMapping("/search/video/{keyword}")
    fun searchConference(@PathVariable keyword: String): ResponseDTO {
        return ResponseDTO()
    }

}