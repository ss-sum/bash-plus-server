package com.bashplus.server.archive.service

import com.bashplus.server.archive.domain.Archive
import com.bashplus.server.archive.dto.ArchiveVideoRequestDTO
import com.bashplus.server.archive.repository.ArchiveRepository
import com.bashplus.server.common.exception.ApiException
import com.bashplus.server.common.exception.ExceptionEnum
import com.bashplus.server.host.domain.Conference
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.users.domain.Users
import com.bashplus.server.users.repository.UsersRepository
import com.bashplus.server.video.domain.Video
import com.bashplus.server.video.repository.VideoRepository
import junit.framework.TestCase.assertTrue
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ArchiveServiceTest {
    @Autowired
    private lateinit var archiveService: ArchiveService

    @Autowired
    private lateinit var usersRepository: UsersRepository

    @Autowired
    private lateinit var conferenceRepository: ConferenceRepository

    @Autowired
    private lateinit var videoRepository: VideoRepository

    @Autowired
    private lateinit var archiveRepository: ArchiveRepository
    private lateinit var conference: Conference
    private lateinit var video: Video
    private lateinit var user: Users

    @BeforeAll
    fun setUp() {
        conference = conferenceRepository.save(Conference("title", "content", LocalDate.now(), LocalDate.now()))
        video = videoRepository.save(Video(conference, "url", "title", "content"))
        user = usersRepository.save(Users("user", "google", "user"))
    }

    @AfterEach
    fun init() {
        archiveRepository.deleteAll()
    }

    @AfterAll
    fun tearDown() {
        archiveRepository.deleteAll()
        videoRepository.deleteAll()
        conferenceRepository.deleteAll()
        usersRepository.deleteAll()
    }

    @DisplayName("사용자의 시청 기록을 조회하는 함수의 정상 동작에 대한 테스트")
    @Test
    fun getVideoWatchRecordSuccessTest() {
        archiveRepository.save(Archive(user, video))
        var archive = archiveService.getVideoWatchRecords(user.uid!!)
        assert(archive.get(0).vid == video.vid)
    }

    @DisplayName("사용자의 시청 기록을 조회하는 함수의 오류 응답에 대한 테스트 - 사용자가 없는 경우")
    @ParameterizedTest
    @ValueSource(longs = [2])
    fun getVideoWatchRecordFailureByUidTest(input: Long) {
        val archive = archiveService.getVideoWatchRecords(input)
        assert(archive.isEmpty())
    }

    @DisplayName("나중에 볼 영상을 등록하는 함수의 정상 동작에 대한 테스트 - 기존 시청 기록이 있는 경우")
    @Test
    fun registerArchiveLastVideoSuccessAlreadyArchiveTest() {
        archiveRepository.save(Archive(user, video, last = false))
        val request = ArchiveVideoRequestDTO() //TODO DTO 형식 변경
        archiveService.registerArchiveLastVideo(request)
        val archive = archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!)
        assert(archive.isPresent)
        assertTrue(archive.get().last)
    }

    @DisplayName("나중에 볼 영상을 등록하는 함수의 정상 동작에 대한 테스트 - 기존 시청 기록이 없는 경우")
    @Test
    fun registerArchiveLastVideoSuccessNoArchiveTest() {
        val request = ArchiveVideoRequestDTO() //TODO DTO 형식 변경
        archiveService.registerArchiveLastVideo(request)
        val archive = archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!)
        assert(archive.isPresent)
        assertTrue(archive.get().last)
    }

    @DisplayName("나중에 볼 영상을 등록하는 함수의 오류 응답에 대한 테스트 - 사용자가 없는 경우")
    @ParameterizedTest
    @ValueSource(longs = [2])
    fun registerArchiveLastVideoFailureByUserTest(uid: Long) {
        val request = ArchiveVideoRequestDTO() //TODO DTO 형식 변경
        assertThrows<ApiException> {
            archiveService.registerArchiveLastVideo(request)
        }.getError().getMessage().equals(ExceptionEnum.BAD_REQUEST.getMessage())
    }

    @DisplayName("나중에 볼 영상을 등록하는 함수의 오류 응답에 대한 테스트 - 비디오가 없는 경우")
    @ParameterizedTest
    @ValueSource(longs = [2])
    fun registerArchiveLastVideoFailureByVideoTest(vid: Long) {
        val request = ArchiveVideoRequestDTO() //TODO DTO 형식 변경
        assertThrows<ApiException> {
            archiveService.registerArchiveLastVideo(request)
        }.getError().getMessage().equals(ExceptionEnum.BAD_REQUEST.getMessage())
    }

    @DisplayName("나중에 볼 영상의 목록을 조회하는 함수의 정상 응답에 대한 테스트")
    @Test
    fun getLastVideosSuccessTest() {
        archiveRepository.save(Archive(user, video, last = true))
        val archive = archiveService.getLastVideos(user.uid!!)
        assert(archive.get(0).vid == video.vid)
    }

    @DisplayName("나중에 볼 영상의 목록을 조회하는 함수의 오류 응답에 대한 테스트 - 사용자가 없는 경우")
    @ParameterizedTest
    @ValueSource(longs = [2])
    fun getLastVideosFailureByUserTest(input: Long) {
        val archive = archiveService.getLastVideos(input)
        assert(archive.isEmpty())
    }

    @DisplayName("좋아요한 영상을 등록하는 함수의 정상 동작에 대한 테스트 - 기존 시청 기록이 있는 경우")
    @Test
    fun registerArchiveLikeVideoSuccessAlreadyArchiveTest() {
        archiveRepository.save(Archive(user, video, like = false))
        val request = ArchiveVideoRequestDTO() //TODO DTO 형식 변경
        archiveService.registerArchiveLikeVideo(request)
        val archive = archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!)
        assert(archive.isPresent)
        assertTrue(archive.get().likes)
    }

    @DisplayName("좋아요한 영상을 등록하는 함수의 정상 동작에 대한 테스트 - 기존 시청 기록이 없는 경우")
    @Test
    fun registerArchiveLikeVideoSuccessNoArchiveTest() {
        val request = ArchiveVideoRequestDTO() //TODO DTO 형식 변경
        archiveService.registerArchiveLikeVideo(request)
        val archive = archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!)
        assert(archive.isPresent)
        assertTrue(archive.get().likes)
    }

    @DisplayName("좋아요한 영상을 등록하는 함수의 오류 응답에 대한 테스트 - 사용자가 없는 경우")
    @ParameterizedTest
    @ValueSource(longs = [2])
    fun registerArchiveLikeVideoFailureByUserTest(uid: Long) {
        val request = ArchiveVideoRequestDTO() //TODO DTO 형식 변경
        assertThrows<ApiException> {
            archiveService.registerArchiveLikeVideo(request)
        }.getError().getMessage().equals(ExceptionEnum.BAD_REQUEST.getMessage())
    }

    @DisplayName("좋아요한 영상을 등록하는 함수의 오류 응답에 대한 테스트 - 비디오가 없는 경우")
    @ParameterizedTest
    @ValueSource(longs = [2])
    fun registerArchiveLikeVideoFailureByVideoTest(vid: Long) {
        val request = ArchiveVideoRequestDTO() //TODO DTO 형식 변경
        assertThrows<ApiException> {
            archiveService.registerArchiveLikeVideo(request)
        }.getError().getMessage().equals(ExceptionEnum.BAD_REQUEST.getMessage())
    }

    @DisplayName("좋아요한 영상의 목록을 조회하는 함수의 정상 응답에 대한 테스트")
    @Test
    fun getLikeVideosSuccessTest() {
        archiveRepository.save(Archive(user, video, like = true))
        val archive = archiveService.getLikeVideos(user.uid!!)
        assert(archive.get(0).vid == video.vid)
    }

    @DisplayName("좋아요한 영상의 목록을 조회하는 함수의 오류 응답에 대한 테스트 - 사용자가 없는 경우")
    @ParameterizedTest
    @ValueSource(longs = [2])
    fun getLikeVideosFailureByUserTest(input: Long) {
        val archive = archiveService.getLikeVideos(input)
        assert(archive.isEmpty())
    }


}