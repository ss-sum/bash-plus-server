package com.bashplus.server.information.service

import com.bashplus.server.common.ResponseListDTO
import com.bashplus.server.host.repository.HostRepository
import com.bashplus.server.information.dto.CategoryInformationDTO
import com.bashplus.server.information.dto.HostInformationDTO
import com.bashplus.server.information.repository.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class InformationService {

    @Autowired
    private lateinit var hostRepository: HostRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    fun getConferenceHosts(page: Pageable): ResponseListDTO<HostInformationDTO> {
        val result = hostRepository.findAll(page)
        return ResponseListDTO(result.toList().map { host -> HostInformationDTO(host) }, page.pageNumber, page.pageSize, result.totalElements)
    }

    fun getCategories(page: Pageable): ResponseListDTO<CategoryInformationDTO> {
        val result = categoryRepository.findAll(page)
        return ResponseListDTO(result.toList().map { category -> CategoryInformationDTO(category) }, page.pageNumber, page.pageSize, result.totalElements)
    }
}