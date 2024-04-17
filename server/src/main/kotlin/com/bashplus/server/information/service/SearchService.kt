package com.bashplus.server.information.service

import com.bashplus.server.common.ResponseListDTO
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.host.repository.HostRepository
import com.bashplus.server.information.dto.CategoryInformationDTO
import com.bashplus.server.information.dto.HostInformationDTO
import com.bashplus.server.information.dto.VideoInformationDTO
import com.bashplus.server.information.repository.CategoryRepository
import com.bashplus.server.video.repository.VideoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class SearchService {
    @Autowired
    private lateinit var videoRepository: VideoRepository

    @Autowired
    private lateinit var conferenceRepository: ConferenceRepository

    @Autowired
    private lateinit var conferenceHostRepository: ConferenceRepository

    @Autowired
    private lateinit var hostRepository: HostRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    fun getAllVideosByConference(keyword: String, page: Pageable): ResponseListDTO<VideoInformationDTO> {
        val result = videoRepository.findAllByConferenceTitle(keyword, page)
        return ResponseListDTO(result.toList().map { video -> VideoInformationDTO(video) }, page.pageNumber, page.pageSize, result.totalElements)
    }

    fun getHostSearchResult(keyword: String, page: Pageable): ResponseListDTO<HostInformationDTO> {
        val result = hostRepository.findAllByCompanyIsLikeIgnoreCase(mappingKeyword(keyword), page)
        return ResponseListDTO(result.toList().map { host -> HostInformationDTO(host) }, page.pageNumber, page.pageSize, result.totalElements)
    }

    fun getCategorySearchResult(keyword: String, page: Pageable): ResponseListDTO<CategoryInformationDTO> {
        val result = categoryRepository.findAllByCategoryIsLikeIgnoreCase(mappingKeyword(keyword), page)
        return ResponseListDTO(result.toList().map { category -> CategoryInformationDTO(category) }, page.pageNumber, page.pageSize, result.totalElements)
    }

    fun getVideoSearchResult(keyword: String, page: Pageable): ResponseListDTO<VideoInformationDTO> {
        val result = videoRepository.findAllByTitleIsLikeIgnoreCase(mappingKeyword(keyword), page)
        return ResponseListDTO(result.toList().map { video -> VideoInformationDTO(video) }, page.pageNumber, page.pageSize, result.totalElements)
    }

    private fun mappingKeyword(keyword: String): String {
        return "%$keyword%"
    }


}