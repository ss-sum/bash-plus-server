package com.bashplus.server.video.service

import com.bashplus.server.archive.domain.Archive
import com.bashplus.server.archive.repository.ArchiveRepository
import com.bashplus.server.common.ResponseListDTO
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

    fun getAllVideos(page: Pageable): ResponseListDTO<VideoDTO> {
        val result = videoRepository.findAll(page)
        return ResponseListDTO(result.toList().map { video ->
            val videoTag = videoTagRepository.findTagByVid(video.vid!!)
            VideoDTO(video, videoTag)
        }, page.pageNumber, page.pageSize, result.totalElements)
    }

    fun getVideoInfo(videoId: Long): VideoDTO {
        val video = videoRepository.findByVid(videoId)
        if (video.isPresent) {
            val videoTag = videoTagRepository.findTagByVid(videoId)
            return VideoDTO(video.get(), videoTag)
        } else {
            throw ApiException(ExceptionEnum.VIDEO_NOT_FOUND)
        }
    }

    fun getVideoCommentInfo(videoId: Long, page: Pageable): ResponseListDTO<CommentDTO> {
        val video = videoRepository.findByVid(videoId)
        if (video.isPresent) {
            val result = commentRepository.findAllByVideoVid(videoId, page)
            return ResponseListDTO(result.toList().map { comment -> CommentDTO(comment) }, page.pageNumber, page.pageSize, result.totalElements)
        } else {
            throw ApiException(ExceptionEnum.VIDEO_NOT_FOUND)
        }
    }

    fun writeComment(request: CommentRequestDTO) {
        val user = usersRepository.findByUid(request.uid).get()
        val video = videoRepository.findByVid(request.vid)
        if (video.isPresent) {
            commentRepository.save(Comment(user, video.get(), request.content))
        } else {
            throw ApiException(ExceptionEnum.VIDEO_NOT_FOUND)
        }
    }

    fun updateWatchRecord(request: WatchRequestDTO) {
        val archive = archiveRepository.findByUserUidAndVideoVid(request.uid, request.vid)
        if (archive.isPresent) {
            archiveRepository.updateArchiveWatchRecord(archive.get().uidvid!!, request.time)
        } else {
            val user = usersRepository.findByUid(request.uid).get()
            val video = videoRepository.findByVid(request.vid)
            if (video.isPresent) {
                archiveRepository.save(Archive(user, video.get(), request.time))
            } else {
                throw ApiException(ExceptionEnum.VIDEO_NOT_FOUND)
            }
        }
    }
}