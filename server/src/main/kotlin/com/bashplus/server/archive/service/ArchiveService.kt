package com.bashplus.server.archive.service

import com.bashplus.server.archive.dto.ArchiveVideoWatchRecordDTO
import com.bashplus.server.archive.repository.ArchiveRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class ArchiveService {
    @Autowired
    private lateinit var archiveRepository: ArchiveRepository

    fun getVideoWatchRecords(userId: Long): List<ArchiveVideoWatchRecordDTO> {
        return archiveRepository.findAllByUserUid(userId).map { archive -> ArchiveVideoWatchRecordDTO(archive) }
    }
}