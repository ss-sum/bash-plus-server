package com.bashplus.server.video.service

import com.bashplus.server.archive.repository.ArchiveRepository
import com.bashplus.server.common.exception.ApiException
import com.bashplus.server.common.exception.ExceptionEnum
import com.bashplus.server.host.domain.Conference
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.information.domain.Category
import com.bashplus.server.information.repository.CategoryRepository
import com.bashplus.server.users.domain.Users
import com.bashplus.server.users.repository.UsersRepository
import com.bashplus.server.video.domain.Comment
import com.bashplus.server.video.domain.Video
import com.bashplus.server.video.domain.VideoTag
import com.bashplus.server.video.dto.CommentRequestDTO
import com.bashplus.server.video.dto.VideoDTO
import com.bashplus.server.video.dto.WatchRequestDTO
import com.bashplus.server.video.repository.CommentRepository
import com.bashplus.server.video.repository.VideoRepository
import com.bashplus.server.video.repository.VideoTagRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.mockito.MockitoAnnotations
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class VideoServiceTest {
    @Autowired
    private lateinit var videoService: VideoService

    @Autowired
    private lateinit var videoRepository: VideoRepository

    @Autowired
    private lateinit var videoTagRepository: VideoTagRepository

    @Autowired
    private lateinit var commentRepository: CommentRepository

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var conferenceRepository: ConferenceRepository

    @Autowired
    private lateinit var archiveRepository: ArchiveRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository


    private lateinit var user: Users
    private lateinit var video: Video
    private lateinit var conference: Conference
    private lateinit var category: Category

    @BeforeAll
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        user = usersRepository.save(Users("user", "google", "user"))
        conference = conferenceRepository.save(Conference("test1", "content", LocalDate.now(), LocalDate.now()))
        video = videoRepository.save(Video(conference, "url", "test1", null))
        commentRepository.save(Comment(user, video, "test"))
        category = categoryRepository.save(Category("spring", 1))
        videoTagRepository.save(VideoTag(video, category))
    }

    @AfterAll
    fun tearDown() {
        archiveRepository.deleteAll()
        commentRepository.deleteAll()
        videoTagRepository.deleteAll()
        usersRepository.deleteAll()
        categoryRepository.deleteAll()
        videoRepository.deleteAll()
        conferenceRepository.deleteAll()
    }

    @DisplayName("비디오 정보를 가져오는 함수 정상 동작에 대한 테스트")
    @Test
    fun getVideoInfoSuccessTest() {
        val videoDTO: VideoDTO = videoService.getVideoInfo(video.vid!!)
        assertEquals(videoDTO.title, video.title)
        assertEquals(videoDTO.category, videoTagRepository.findTagByVid(video.vid!!).get(0))
    }

    @DisplayName("비디오 정보를 가져오는 함수 비디오가 없을 때 예외처리 테스트")
    @ParameterizedTest
    @ValueSource(longs = [2, 3])
    fun getVideoInfoFailureByVidTest(vid: Long) {
        assertThrows<ApiException> {
            videoService.getVideoInfo(vid)
        }.getError().getMessage().equals(ExceptionEnum.BAD_REQUEST.getMessage())
    }

    @DisplayName("비디오 댓글 정보를 가져오는 함수 정상 동작에 대한 테스트")
    @Test
    fun getVideoCommentInfoSuccessTest() {
        val commentDTOList = videoService.getVideoCommentInfo(video.vid!!)
        assertEquals(commentDTOList.get(0).content, commentRepository.findAllByVideoVid(video.vid!!).get(0).content)
    }

    @DisplayName("비디오 댓글 정보를 가져오는 함수 비디오가 없을 때 예외처리 테스트")
    @ParameterizedTest
    @ValueSource(longs = [2, 3])
    fun getVideoCommentInfoFailureByVidTest(vid: Long) {
        assertThrows<ApiException> {
            videoService.getVideoCommentInfo(vid)
        }.getError().getMessage().equals(ExceptionEnum.BAD_REQUEST.getMessage())
    }

    @DisplayName("비디오 댓글을 작성하는 함수 정상 동작에 대한 테스트")
    @Test
    fun writeCommentSuccessTest() {
        val comment = CommentRequestDTO() //TODO DTO 형식 수정
        videoService.writeComment(comment)
        val commentList = commentRepository.findAllByVideoVid(video.vid!!)
        assertEquals(commentList.get(1).content, "test1")
    }

    @DisplayName("비디오 댓글을 작성하는 함수 비디오가 없을 때 예외처리 테스트")
    @ParameterizedTest
    @ValueSource(longs = [2, 3])
    fun writeCommentFailureByVidTest(vid: Long) {
        val comment = CommentRequestDTO() //TODO DTO 형식 수정
        assertThrows<ApiException> {
            videoService.writeComment(comment)
        }.getError().getMessage().equals(ExceptionEnum.BAD_REQUEST.getMessage())
    }

    @DisplayName("비디오 댓글을 작성하는 함수 유저가 없을 때 예외처리 테스트")
    @ParameterizedTest
    @ValueSource(longs = [2, 3])
    fun writeCommentFailureByUidTest(uid: Long) {
        val comment = CommentRequestDTO() //TODO DTO 형식 수정
        assertThrows<ApiException> {
            videoService.writeComment(comment)
        }.getError().getMessage().equals(ExceptionEnum.BAD_REQUEST.getMessage())
    }

    @DisplayName("비디오 시청 기록을 업데이트하는 함수 정상 동작에 대한 테스트")
    @Test
    fun updateWatchRecordSuccessTest() {
        val request = WatchRequestDTO() //TODO DTO 형식 수정
        videoService.updateWatchRecord(request)
        val archive = archiveRepository.findByUserUidAndVideoVid(request.uid, request.vid)
        assertEquals(archive.get().time, request.time)
    }

    @DisplayName("비디오 시청 기록을 업데이트하는 함수 비디오가 없을 때 예외처리 테스트")
    @ParameterizedTest
    @ValueSource(longs = [2, 3])
    fun updateWatchRecordFailureByVidTest(vid: Long) {
        val request = WatchRequestDTO() //TODO DTO 형식 수정
        assertThrows<ApiException> {
            videoService.updateWatchRecord(request)
        }.getError().getMessage().equals(ExceptionEnum.BAD_REQUEST.getMessage())
    }

    @DisplayName("비디오 시청 기록을 업데이트하는 함수 유저가 없을 때 예외처리 테스트")
    @ParameterizedTest
    @ValueSource(longs = [2, 3])
    fun updateWatchRecordFailureByUidTest(uid: Long) {
        val request = WatchRequestDTO() //TODO DTO 형식 수정
        assertThrows<ApiException> {
            videoService.updateWatchRecord(request)
        }.getError().getMessage().equals(ExceptionEnum.BAD_REQUEST.getMessage())
    }
}