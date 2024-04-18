package com.bashplus.server.curation.controller

import com.bashplus.server.common.ResponseDTO
import io.swagger.v3.oas.annotations.Hidden
import io.swagger.v3.oas.annotations.responses.ApiResponse
import io.swagger.v3.oas.annotations.responses.ApiResponses
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "큐레이션 API", description = "회원 맞춤 큐레이션 관련 API")
@RestController
@RequestMapping("/curation")
class CurationController {
    @Hidden
    @GetMapping("/video/user/{userId}/recommendation")
    @ApiResponses(value = [
        ApiResponse(responseCode = "200", description = "OK"),
        ApiResponse(responseCode = "400", description = "BAD REQUEST"),
        ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
    ])
    fun getRecommendedVideos(@PathVariable userId: String): ResponseDTO<String> {
        return ResponseDTO("")
    }

}