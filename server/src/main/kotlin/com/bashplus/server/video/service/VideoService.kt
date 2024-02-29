package com.bashplus.server.video.service

import com.bashplus.server.common.exception.ApiException
import com.bashplus.server.common.exception.ExceptionEnum
import com.bashplus.server.users.repository.UsersRepository
import com.bashplus.server.video.domain.Comment
import com.bashplus.server.video.dto.CommentDTO
import com.bashplus.server.video.dto.CommentRequestDTO
import com.bashplus.server.video.dto.VideoDTO
import com.bashplus.server.video.repository.CommentRepository
import com.bashplus.server.video.repository.VideoRepository
import com.bashplus.server.video.repository.VideoTagRepository
import org.springframework.beans.factory.annotation.Autowired
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

    fun getVideoInfo(videoId: Long): VideoDTO {
        val video = videoRepository.findByVid(videoId)
        if (video.isPresent) {
            val videoTag = videoTagRepository.findTagByVid(videoId)
            return VideoDTO(video.get(), videoTag)
        } else {
            throw ApiException(ExceptionEnum.BAD_REQUEST)
        }
    }

    fun getVideoCommentInfo(videoId: Long): List<CommentDTO> {
        return commentRepository.findAllByVideoVid(videoId).map { comment -> CommentDTO(comment) }
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
}