package com.bashplus.server.information.controller

import com.bashplus.server.common.ResponseListDTO
import com.bashplus.server.information.service.SearchService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.web.bind.annotation.*

@Tag(name = "검색 API", description = "검색 관련 API")
@RestController
@RequestMapping("/search")
class SearchController {
    @Autowired
    private lateinit var searchService: SearchService

    @Operation(summary = "컨퍼런스별 전체 영상 목록 조회 API", description = "")
    @GetMapping("/conference/{keyword}/videos")
    fun getWholeVideos(@PathVariable keyword: String, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = searchService.getAllVideosByConference(keyword, pageable)
        return result
    }


    @Operation(summary = "컨퍼런스 주최 검색 API", description = "")
    @GetMapping("/conference/host/{keyword}")
    fun searchHost(@PathVariable keyword: String, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = searchService.getHostSearchResult(keyword, pageable)
        return result
    }

    @Operation(summary = "컨퍼런스 카테고리 검색 API", description = "")
    @GetMapping("/category/{keyword}")
    fun searchCategory(@PathVariable keyword: String, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = searchService.getCategorySearchResult(keyword, pageable)
        return result
    }

    @Operation(summary = "컨퍼런스 비디오 제목 검색 API", description = "")
    @GetMapping("/conference/video/{keyword}")
    fun searchConference(@PathVariable keyword: String, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = searchService.getVideoSearchResult(keyword, pageable)
        return result
    }


}