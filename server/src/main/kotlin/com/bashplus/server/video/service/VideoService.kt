package com.bashplus.server.video.service

import com.bashplus.server.archive.domain.Archive
import com.bashplus.server.archive.repository.ArchiveRepository
import com.bashplus.server.common.exception.ApiException
import com.bashplus.server.common.exception.ExceptionEnum
import com.bashplus.server.users.repository.UsersRepository
import com.bashplus.server.video.domain.Comment
import com.bashplus.server.video.dto.CommentDTO
import com.bashplus.server.video.dto.CommentRequestDTO
import com.bashplus.server.video.dto.VideoDTO
import com.bashplus.server.video.dto.WatchRequestDTO
import com.bashplus.server.video.repository.CommentRepository
import com.bashplus.server.video.repository.VideoRepository
import com.bashplus.server.video.repository.VideoTagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class VideoService {
    @Autowired
    private lateinit var videoRepository: VideoRepository

    @Autowired
    private lateinit var videoTagRepository: VideoTagRepository

    @Autowired
    private lateinit var commentRepository: CommentRepository

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var archiveRepository: ArchiveRepository

    fun getVideoInfo(videoId: Long): VideoDTO {
        val video = videoRepository.findByVid(videoId)
        if (video.isPresent) {
            val videoTag = videoTagRepository.findTagByVid(videoId)
            return VideoDTO(video.get(), videoTag)
        } else {
            throw ApiException(ExceptionEnum.BAD_REQUEST)
        }
    }

    fun getVideoCommentInfo(videoId: Long, page: Pageable): List<CommentDTO> {
        val video = videoRepository.findByVid(videoId)
        if (video.isPresent) {
            return commentRepository.findAllByVideoVid(videoId, page).toList().map { comment -> CommentDTO(comment) }
        } else {
            throw ApiException(ExceptionEnum.BAD_REQUEST)
        }
    }

    fun writeComment(request: CommentRequestDTO) {
        val user = usersRepository.findByUid(request.uid)
        val video = videoRepository.findByVid(request.vid)
        if (user.isPresent && video.isPresent) {
            commentRepository.save(Comment(user.get(), video.get(), request.content))
        } else {
            throw ApiException(ExceptionEnum.BAD_REQUEST)
        }
    }

    fun updateWatchRecord(request: WatchRequestDTO) {
        val archive = archiveRepository.findByUserUidAndVideoVid(request.uid, request.vid)
        if (archive.isPresent) {
            archiveRepository.updateArchiveWatchRecord(archive.get().uidvid!!, request.time)
        } else {
            val user = usersRepository.findByUid(request.uid)
            val video = videoRepository.findByVid(request.vid)
            if (user.isPresent && video.isPresent) {
                archiveRepository.save(Archive(user.get(), video.get(), request.time))
            } else {
                throw ApiException(ExceptionEnum.BAD_REQUEST)
            }
        }
    }
}