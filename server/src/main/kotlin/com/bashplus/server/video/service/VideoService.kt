package com.bashplus.server.video.service

import com.bashplus.server.common.exception.ApiException
import com.bashplus.server.common.exception.ExceptionEnum
import com.bashplus.server.video.dto.VideoDTO
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

    fun getVideoInfo(videoId: Long): VideoDTO {
        val video = videoRepository.findByVid(videoId)
        if (video.isPresent) {
            val videoTag = videoTagRepository.findTagByVid(videoId)
            return VideoDTO(video.get(), videoTag)
        } else {
            throw ApiException(ExceptionEnum.BAD_REQUEST)
        }
    }
}