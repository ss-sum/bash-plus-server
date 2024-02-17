package com.bashplus.server.curation.controller

import com.bashplus.server.common.ResponseDTO
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Tag(name = "큐레이션 API", description = "회원 맞춤 큐레이션 관련 API")
@RestController
@RequestMapping("/curation")
class CurationController {
    @GetMapping("/video/user/{userId}/recommendation")
    fun getRecommendedVideos(@PathVariable userId: String): ResponseDTO {
        return ResponseDTO()
    }

}