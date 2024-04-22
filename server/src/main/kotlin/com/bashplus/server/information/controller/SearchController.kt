package com.bashplus.server.information.controller

import com.bashplus.server.common.ResponseListDTO
import com.bashplus.server.common.exception.ApiExceptionEntity
import com.bashplus.server.information.dto.CategoryInformationDTO
import com.bashplus.server.information.dto.VideoInformationDTO
import com.bashplus.server.information.service.SearchService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
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

    @Operation(summary = "컨퍼런스별 전체 영상 목록 조회 API", description = "영상 목록을 컨퍼런스별로 조회할 수 있게 해주는 영상 조회 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @GetMapping("/conference/{keyword}/videos")
    fun getWholeVideos(@PathVariable keyword: String, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO<VideoInformationDTO> {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = searchService.getAllVideosByConference(keyword, pageable)
        return result
    }


    @Operation(summary = "컨퍼런스 주최 검색 API", description = "컨퍼런스를 호스트를 기준으로 검색하게 할 수 있게 해주는 검색 API")
    @GetMapping("/conference/host/{keyword}")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    fun searchHost(@PathVariable keyword: String, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO<VideoInformationDTO> {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = searchService.getHostSearchResult(keyword, pageable)
        return result
    }

    @Operation(summary = "컨퍼런스 카테고리 검색 API", description = "컨퍼런스의 카테고리를 검색하게 해주는 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @GetMapping("/category/{keyword}")
    fun searchCategory(@PathVariable keyword: String, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO<CategoryInformationDTO> {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = searchService.getCategorySearchResult(keyword, pageable)
        return result
    }

    @Operation(summary = "컨퍼런스 비디오 제목 검색 API", description = "컨퍼런스 비디오의 제목으로 검색하게 해주는 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", useReturnTypeSchema = true),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @GetMapping("/conference/video/{keyword}")
    fun searchConference(@PathVariable keyword: String, @RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO<VideoInformationDTO> {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = searchService.getVideoSearchResult(keyword, pageable)
        return result
    }


}