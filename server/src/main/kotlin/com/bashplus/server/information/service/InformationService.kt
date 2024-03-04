package com.bashplus.server.information.service

import com.bashplus.server.host.repository.HostRepository
import com.bashplus.server.information.dto.HostInformationDTO
import com.bashplus.server.information.repository.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class InformationService {

    @Autowired
    private lateinit var hostRepository: HostRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    fun getConferenceHosts(): List<HostInformationDTO> {
        var result = hostRepository.findAll().map { host -> HostInformationDTO(host) }
        return result
    }

}