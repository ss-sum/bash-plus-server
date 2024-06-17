package com.bashplus.server.video.service

import com.bashplus.server.archive.domain.Archive
import com.bashplus.server.archive.repository.ArchiveRepository
import com.bashplus.server.common.ResponseListDTO
import com.bashplus.server.common.SortingEnum
import com.bashplus.server.common.exception.ApiException
import com.bashplus.server.common.exception.ExceptionEnum
import com.bashplus.server.users.repository.UsersRepository
import com.bashplus.server.video.domain.Comment
import com.bashplus.server.video.domain.CommentLike
import com.bashplus.server.video.domain.Video
import com.bashplus.server.video.dto.*
import com.bashplus.server.video.repository.CommentLikeRepository
import com.bashplus.server.video.repository.CommentRepository
import com.bashplus.server.video.repository.VideoRepository
import com.bashplus.server.video.repository.VideoTagRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
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

    @Autowired
    private lateinit var commentLikeRepository: CommentLikeRepository

    fun getAllVideos(order: VideoOrderEnum, sort: SortingEnum, page: Pageable): ResponseListDTO<VideoDTO> {
        lateinit var result: Page<Video>
        if (order == VideoOrderEnum.DATE) {
            if (sort == SortingEnum.DESC) {
                result = videoRepository.findAllByOrderByConferenceStartAtTimeDesc(page)
            } else {
                result = videoRepository.findAllByOrderByConferenceStartAtTimeAsc(page)
            }
        } else {
            if (sort == SortingEnum.DESC) {
                result = videoRepository.findAllByLikeDesc(page)
            } else {
                result = videoRepository.findAllByLikeAsc(page)
            }
        }
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
            return ResponseListDTO(
                result.toList().map { comment -> CommentDTO(comment) },
                page.pageNumber,
                page.pageSize,
                result.totalElements
            )
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

    fun updateComment(request: CommentRequestDTO) {
        val comment = commentRepository.findByCid(request.cid)
        if (comment.isPresent) {
            comment.get().update(request)
            commentRepository.save(comment.get())
        } else {
            throw ApiException(ExceptionEnum.COMMENT_NOT_FOUND)
        }
    }

    fun deleteComment(commentId: Long) {
        val comment = commentRepository.findByCid(commentId)
        if (comment.isPresent) {
            commentRepository.delete(comment.get())
        } else {
            throw ApiException(ExceptionEnum.COMMENT_NOT_FOUND)
        }
    }

    fun likeComment(request: CommentRequestDTO) {
        val comment = commentRepository.findByCid(request.cid)
        if (comment.isPresent) {
            val existedValue = commentLikeRepository.findByCommentCidAndUserUid(request.cid, request.uid)
            if (existedValue.isEmpty()) {
                val commentLike = CommentLike(comment.get(), usersRepository.findByUid(request.uid).get())
                comment.get().like()
                commentRepository.save(comment.get())
                commentLikeRepository.save(commentLike)
            } else {
                comment.get().unlike()
                commentRepository.save(comment.get())
                commentLikeRepository.delete(existedValue.get())
            }
        } else {
            throw ApiException(ExceptionEnum.COMMENT_NOT_FOUND)
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