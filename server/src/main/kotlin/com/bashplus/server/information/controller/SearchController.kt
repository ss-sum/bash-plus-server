package com.bashplus.server.information.controller

import com.bashplus.server.common.ResponseDTO
import com.bashplus.server.information.service.SearchService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "검색 API", description = "검색 관련 API")
@RestController
@RequestMapping("/search")
class SearchController {
    @Autowired
    private lateinit var searchService: SearchService

    @Operation(summary = "전체 영상 목록 조회 API", description = "")
    @GetMapping("/conference/video")
    fun getWholeVideos(): ResponseDTO {
        val result = searchService.getAllVideos()
        return ResponseDTO(result)
    }


    @Operation(summary = "컨퍼런스 주최 검색 API", description = "")
    @GetMapping("/conference/host/{keyword}")
    fun searchHost(@PathVariable keyword: String): ResponseDTO {
        val result = searchService.getHostSearchResult(keyword)
        return ResponseDTO(result)
    }

    @Operation(summary = "컨퍼런스 카테고리 검색 API", description = "")
    @GetMapping("/category/{keyword}")
    fun searchCategory(@PathVariable keyword: String): ResponseDTO {
        val result = searchService.getCategorySearchResult(keyword)
        return ResponseDTO(result)
    }

    @Operation(summary = "컨퍼런스 비디오 제목 검색 API", description = "")
    @GetMapping("/conference/video/{keyword}")
    fun searchConference(@PathVariable keyword: String): ResponseDTO {
        val result = searchService.getVideoSearchResult(keyword)
        return ResponseDTO(result)
    }

}