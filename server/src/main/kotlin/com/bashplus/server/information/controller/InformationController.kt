package com.bashplus.server.information.controller

import com.bashplus.server.common.ResponseListDTO
import com.bashplus.server.common.exception.ApiExceptionEntity
import com.bashplus.server.information.dto.CategoryInformationDTO
import com.bashplus.server.information.dto.HostInformationDTO
import com.bashplus.server.information.service.InformationService
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

@Tag(name = "정보 API", description = "목록 조회 관련 API")
@RestController
@RequestMapping("/information")
class InformationController {
    @Autowired
    private lateinit var informationService: InformationService

    @Operation(summary = "컨퍼런스 주최 목록 API", description = "검색 시 컨퍼런스 호스트 선택지 제공을 위한 목록 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = HostInformationDTO::class))]),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @GetMapping("/conference/hosts")
    fun getHosts(@RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = informationService.getConferenceHosts(pageable)
        return result
    }

    @Operation(summary = "카테고리 목록 API", description = "검색 시 카테고리 선택지 제공을 위한 목록 API")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK", content = [Content(schema = Schema(implementation = CategoryInformationDTO::class))]),
        ApiResponse(responseCode = "400", description = "BAD REQUEST", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))]),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR", content = [Content(schema = Schema(implementation = ApiExceptionEntity::class))])
    ])
    @GetMapping("/conference/categories")
    fun getCategory(@RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = informationService.getCategories(pageable)
        return result
    }

}