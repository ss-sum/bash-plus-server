package com.bashplus.server.information.controller

import com.bashplus.server.common.ResponseListDTO
import com.bashplus.server.information.service.InformationService
import io.swagger.v3.oas.annotations.Operation
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

    @Operation(summary = "컨퍼런스 주최 목록 API", description = "검색 시 선택지 제공을 위한 목록 API")
    @GetMapping("/conference/hosts")
    fun getHosts(@RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = informationService.getConferenceHosts(pageable)
        return result
    }

    @Operation(summary = "카테고리 목록 API", description = "검색 시 선택지 제공을 위한 목록 API")
    @GetMapping("/conference/categories")
    fun getCategory(@RequestParam pageSize: Int, @RequestParam pageNum: Int): ResponseListDTO {
        val pageable: Pageable = PageRequest.of(pageNum, pageSize)
        val result = informationService.getCategories(pageable)
        return result
    }

}