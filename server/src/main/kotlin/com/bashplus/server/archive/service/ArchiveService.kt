package com.bashplus.server.archive.service

import com.bashplus.server.archive.domain.Archive
import com.bashplus.server.archive.dto.ArchiveVideoDTO
import com.bashplus.server.archive.dto.ArchiveVideoRequestDTO
import com.bashplus.server.archive.dto.ArchiveVideoWatchRecordDTO
import com.bashplus.server.archive.repository.ArchiveRepository
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

    fun getVideoWatchRecords(userId: Long, page: Pageable): List<ArchiveVideoWatchRecordDTO> {
        val user = usersRepository.findByUid(userId)
        if (!user.isPresent) {
            throw ApiException(ExceptionEnum.BAD_REQUEST)
        } else {
            return archiveRepository.findAllByUserUid(userId, page).toList().map { archive -> ArchiveVideoWatchRecordDTO(archive) }
        }
    }

    fun registerArchiveLastVideo(archiveVideoRequestDTO: ArchiveVideoRequestDTO) {
        val result = archiveRepository.findByUserUidAndVideoVid(archiveVideoRequestDTO.uid, archiveVideoRequestDTO.vid)
        if (result.isPresent) {
            archiveRepository.updateArchiveLast(result.get().uidvid ?: 0, !result.get().last)
        } else {
            val user = usersRepository.findByUid(archiveVideoRequestDTO.uid)
            var video = videoRepository.findByVid(archiveVideoRequestDTO.vid)
            if (user.isPresent && video.isPresent) {
                archiveRepository.save(Archive(user.get(), video.get(), last = true))
            } else {
                throw ApiException(ExceptionEnum.BAD_REQUEST)
            }

        }
    }

    fun getLastVideos(userId: Long, page: Pageable): List<ArchiveVideoDTO> {
        val user = usersRepository.findByUid(userId)
        if (!user.isPresent) {
            throw ApiException(ExceptionEnum.BAD_REQUEST)
        } else {
            return archiveRepository.findAllByUserUidAndLastIsTrue(userId, page).toList().map { archive -> ArchiveVideoDTO(archive) }
        }
    }

    fun registerArchiveLikeVideo(archiveVideoRequestDTO: ArchiveVideoRequestDTO) {
        val result = archiveRepository.findByUserUidAndVideoVid(archiveVideoRequestDTO.uid, archiveVideoRequestDTO.vid)
        if (result.isPresent) {
            archiveRepository.updateArchiveLiked(result.get().uidvid ?: 0, !result.get().likes)
        } else {
            val user = usersRepository.findByUid(archiveVideoRequestDTO.uid)
            var video = videoRepository.findByVid(archiveVideoRequestDTO.vid)
            if (user.isPresent && video.isPresent) {
                archiveRepository.save(Archive(user.get(), video.get(), like = true))
            } else {
                throw ApiException(ExceptionEnum.BAD_REQUEST)
            }
        }
    }

    fun getLikeVideos(userId: Long, page: Pageable): List<ArchiveVideoDTO> {
        val user = usersRepository.findByUid(userId)
        if (!user.isPresent) {
            throw ApiException(ExceptionEnum.BAD_REQUEST)
        } else {
            return archiveRepository.findAllByUserUidAndLikesIsTrue(userId, page).toList().map { archive -> ArchiveVideoDTO(archive) }
        }
    }

    fun registerArchiveTimeStamp(archiveVideoRequestDTO: ArchiveVideoRequestDTO) {
        //TODO, 미정
    }
}