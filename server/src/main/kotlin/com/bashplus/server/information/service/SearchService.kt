package com.bashplus.server.information.service

import com.bashplus.server.common.OrderByEnum
import com.bashplus.server.common.ResponseListDTO
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.host.repository.HostRepository
import com.bashplus.server.information.dto.VideoInformationDTO
import com.bashplus.server.information.repository.CategoryRepository
import com.bashplus.server.video.domain.Video
import com.bashplus.server.video.repository.VideoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
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

    fun getConferenceSearchResult(keyword: String, order: OrderByEnum, page: Pageable): ResponseListDTO<VideoInformationDTO> {
        lateinit var result: Page<Video>
        if (order == OrderByEnum.DATE) {
            result = videoRepository.findAllByConferenceTitleIsLikeIgnoreCase(mappingKeyword(keyword), page)
        } else {
            result = videoRepository.findAllByConferenceTitleIsLikeIgnoreCaseOrderByLike(mappingKeyword(keyword), page)
        }
        return ResponseListDTO(result.toList().map { video -> VideoInformationDTO(video) }, page.pageNumber, page.pageSize, result.totalElements)
    }

    fun getHostSearchResult(keyword: String, order: OrderByEnum, page: Pageable): ResponseListDTO<VideoInformationDTO> {
        lateinit var result: Page<Video>
        if (order == OrderByEnum.DATE) {
            result = videoRepository.findAllByConferenceHost(mappingKeyword(keyword), page)
        } else {
            result = videoRepository.findAllByConferenceHostOrderByLike(mappingKeyword(keyword), page)
        }
        return ResponseListDTO(result.toList().map { video -> VideoInformationDTO(video) }, page.pageNumber, page.pageSize, result.totalElements)
    }

    fun getCategorySearchResult(keyword: String, order: OrderByEnum, page: Pageable): ResponseListDTO<VideoInformationDTO> {
        lateinit var result: Page<Video>
        if (order == OrderByEnum.DATE) {
            result = videoRepository.findAllByVideoCategory(mappingKeyword(keyword), page)
        } else {
            result = videoRepository.findAllByVideoCategoryOrderByLike(mappingKeyword(keyword), page)
        }
        return ResponseListDTO(result.toList().map { video -> VideoInformationDTO(video) }, page.pageNumber, page.pageSize, result.totalElements)
    }

    fun getVideoSearchResult(keyword: String, order: OrderByEnum, page: Pageable): ResponseListDTO<VideoInformationDTO> {
        lateinit var result: Page<Video>
        if (order == OrderByEnum.DATE) {
            result = videoRepository.findAllByTitleIsLikeIgnoreCaseOrderByConferenceStartAtTimeDesc(mappingKeyword(keyword), page)
        } else {
            result = videoRepository.findAllByTitleIsLikeIgnoreCaseOrderByLike(mappingKeyword(keyword), page)
        }
        return ResponseListDTO(result.toList().map { video -> VideoInformationDTO(video) }, page.pageNumber, page.pageSize, result.totalElements)
    }

    private fun mappingKeyword(keyword: String): String {
        return "%$keyword%"
    }


}