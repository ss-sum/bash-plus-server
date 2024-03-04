package com.bashplus.server.information.service

import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.host.repository.HostRepository
import com.bashplus.server.information.dto.CategoryInformationDTO
import com.bashplus.server.information.dto.HostInformationDTO
import com.bashplus.server.information.dto.VideoInformationDTO
import com.bashplus.server.information.repository.CategoryRepository
import com.bashplus.server.video.repository.VideoRepository
import org.springframework.beans.factory.annotation.Autowired
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

    fun getAllVideos(): List<VideoInformationDTO> {
        val result = videoRepository.findAll().map { video -> VideoInformationDTO(video) }
        return result
    }

    fun getHostSearchResult(keyword: String): List<HostInformationDTO> {
        val result = hostRepository.findAllByCompanyIsLike(keyword).map { host -> HostInformationDTO(host) }
        return result
    }

    fun getCategorySearchResult(keyword: String): List<CategoryInformationDTO> {
        val result = categoryRepository.findAllByCategoryIsLike(keyword).map { category -> CategoryInformationDTO(category) }
        return result
    }


}