package com.bashplus.server.information.service

import com.bashplus.server.common.ResponseListDTO
import com.bashplus.server.common.SortingEnum
import com.bashplus.server.information.dto.OrderByEnum
import com.bashplus.server.information.dto.VideoInformationDTO
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

    fun getConferenceSearchResult(keyword: String, order: OrderByEnum, sort: SortingEnum, page: Pageable): ResponseListDTO<VideoInformationDTO> {
        lateinit var result: Page<Video>
        if (order == OrderByEnum.DATE) {
            if (sort == SortingEnum.DESC) {
                result = videoRepository.findAllByConferenceTitleIsLikeIgnoreCaseOrderByConferenceStartAtTimeDesc(mappingKeyword(keyword), page)
            } else {
                result = videoRepository.findAllByConferenceTitleIsLikeIgnoreCaseOrderByConferenceStartAtTimeAsc(mappingKeyword(keyword), page)
            }
        } else {
            if (sort == SortingEnum.DESC) {
                result = videoRepository.findAllByConferenceTitleIsLikeIgnoreCaseOrderByLike(mappingKeyword(keyword), page)
            } else {
                result = videoRepository.findAllByConferenceTitleIsLikeIgnoreCaseOrderByLikeAsc(mappingKeyword(keyword), page)
            }
        }
        return ResponseListDTO(result.toList().map { video -> VideoInformationDTO(video) }, page.pageNumber, page.pageSize, result.totalElements)
    }

    fun getHostSearchResult(keyword: String, order: OrderByEnum, sort: SortingEnum, page: Pageable): ResponseListDTO<VideoInformationDTO> {
        lateinit var result: Page<Video>
        if (order == OrderByEnum.DATE) {
            if (sort == SortingEnum.DESC) {
                result = videoRepository.findAllByConferenceHost(mappingKeyword(keyword), page)
            } else {
                result = videoRepository.findAllByConferenceHostAsc(mappingKeyword(keyword), page)
            }
        } else {
            if (sort == SortingEnum.DESC) {
                result = videoRepository.findAllByConferenceHostOrderByLike(mappingKeyword(keyword), page)
            } else {
                result = videoRepository.findAllByConferenceHostOrderByLikeAsc(mappingKeyword(keyword), page)
            }
        }
        return ResponseListDTO(result.toList().map { video -> VideoInformationDTO(video) }, page.pageNumber, page.pageSize, result.totalElements)
    }

    fun getCategorySearchResult(keyword: String, order: OrderByEnum, sort: SortingEnum, page: Pageable): ResponseListDTO<VideoInformationDTO> {
        lateinit var result: Page<Video>
        if (order == OrderByEnum.DATE) {
            if (sort == SortingEnum.DESC) {
                result = videoRepository.findAllByVideoCategory(mappingKeyword(keyword), page)
            } else {
                result = videoRepository.findAllByVideoCategoryAsc(mappingKeyword(keyword), page)
            }
        } else {
            if (sort == SortingEnum.DESC) {
                result = videoRepository.findAllByVideoCategoryOrderByLike(mappingKeyword(keyword), page)
            } else {
                result = videoRepository.findAllByVideoCategoryOrderByLikeAsc(mappingKeyword(keyword), page)
            }
        }
        return ResponseListDTO(result.toList().map { video -> VideoInformationDTO(video) }, page.pageNumber, page.pageSize, result.totalElements)
    }

    fun getVideoSearchResult(keyword: String, order: OrderByEnum, sort: SortingEnum, page: Pageable): ResponseListDTO<VideoInformationDTO> {
        lateinit var result: Page<Video>
        if (order == OrderByEnum.DATE) {
            if (sort == SortingEnum.DESC) {
                result = videoRepository.findAllByTitleIsLikeIgnoreCaseOrderByConferenceStartAtTimeDesc(mappingKeyword(keyword), page)
            } else {
                result = videoRepository.findAllByTitleIsLikeIgnoreCaseOrderByConferenceStartAtTimeAsc(mappingKeyword(keyword), page)
            }
        } else {
            if (sort == SortingEnum.DESC) {
                result = videoRepository.findAllByTitleIsLikeIgnoreCaseOrderByLike(mappingKeyword(keyword), page)
            } else {
                result = videoRepository.findAllByTitleIsLikeIgnoreCaseOrderByLikeAsc(mappingKeyword(keyword), page)
            }
        }
        return ResponseListDTO(result.toList().map { video -> VideoInformationDTO(video) }, page.pageNumber, page.pageSize, result.totalElements)
    }

    private fun mappingKeyword(keyword: String): String {
        return "%$keyword%"
    }


}