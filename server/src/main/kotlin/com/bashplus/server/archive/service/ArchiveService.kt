package com.bashplus.server.archive.service

import com.bashplus.server.archive.domain.Archive
import com.bashplus.server.archive.dto.ArchiveVideoDTO
import com.bashplus.server.archive.dto.ArchiveVideoRequestDTO
import com.bashplus.server.archive.dto.ArchiveVideoWatchRecordDTO
import com.bashplus.server.archive.repository.ArchiveRepository
import com.bashplus.server.common.ResponseListDTO
import com.bashplus.server.common.exception.ApiException
import com.bashplus.server.common.exception.ExceptionEnum
import com.bashplus.server.users.repository.UsersRepository
import com.bashplus.server.video.repository.VideoRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ArchiveService {
    @Autowired
    private lateinit var archiveRepository: ArchiveRepository

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var videoRepository: VideoRepository

    fun getVideoWatchRecords(userId: Long, page: Pageable): ResponseListDTO {
        val result = archiveRepository.findAllByUserUid(userId, page)
        return ResponseListDTO(result.toList().map { archive -> ArchiveVideoWatchRecordDTO(archive) }, page.pageNumber, page.pageSize, result.totalElements)

    }

    fun updateArchiveLastVideo(archiveVideoRequestDTO: ArchiveVideoRequestDTO) {
        val result = archiveRepository.findByUserUidAndVideoVid(archiveVideoRequestDTO.uid, archiveVideoRequestDTO.vid)
        if (result.isPresent) {
            archiveRepository.updateArchiveLast(result.get().uidvid ?: 0, !result.get().last)
        } else {
            val user = usersRepository.findByUid(archiveVideoRequestDTO.uid).get()
            var video = videoRepository.findByVid(archiveVideoRequestDTO.vid)
            if (video.isPresent) {
                archiveRepository.save(Archive(user, video.get(), last = true))
            } else {
                throw ApiException(ExceptionEnum.BAD_REQUEST)
            }

        }
    }

    fun getLastVideos(userId: Long, page: Pageable): ResponseListDTO {
        val result = archiveRepository.findAllByUserUidAndLastIsTrue(userId, page)
        return ResponseListDTO(result.toList().map { archive -> ArchiveVideoDTO(archive) }, page.pageNumber, page.pageSize, result.totalElements)
    }

    fun updateArchiveLikeVideo(archiveVideoRequestDTO: ArchiveVideoRequestDTO) {
        val result = archiveRepository.findByUserUidAndVideoVid(archiveVideoRequestDTO.uid, archiveVideoRequestDTO.vid)
        if (result.isPresent) {
            archiveRepository.updateArchiveLiked(result.get().uidvid ?: 0, !result.get().likes)
        } else {
            val user = usersRepository.findByUid(archiveVideoRequestDTO.uid).get()
            var video = videoRepository.findByVid(archiveVideoRequestDTO.vid)
            if (video.isPresent) {
                archiveRepository.save(Archive(user, video.get(), like = true))
            } else {
                throw ApiException(ExceptionEnum.BAD_REQUEST)
            }
        }
    }

    fun getLikeVideos(userId: Long, page: Pageable): ResponseListDTO {
        val result = archiveRepository.findAllByUserUidAndLikesIsTrue(userId, page)
        return ResponseListDTO(result.toList().map { archive -> ArchiveVideoDTO(archive) }, page.pageNumber, page.pageSize, result.totalElements)
    }

    fun registerArchiveTimeStamp(archiveVideoRequestDTO: ArchiveVideoRequestDTO) {
        //TODO, 미정
    }
}