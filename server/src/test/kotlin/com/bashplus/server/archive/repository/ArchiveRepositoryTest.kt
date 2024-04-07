package com.bashplus.server.archive.repository

import com.bashplus.server.archive.domain.Archive
import com.bashplus.server.archive.service.ArchiveService
import com.bashplus.server.host.domain.Conference
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.users.domain.Users
import com.bashplus.server.users.repository.UsersRepository
import com.bashplus.server.video.domain.Video
import com.bashplus.server.video.repository.VideoRepository
import junit.framework.TestCase.assertTrue
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import java.time.LocalTime

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class ArchiveRepositoryTest {
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
    private lateinit var archive: Archive

    @BeforeAll
    fun setUp() {
        conference = conferenceRepository.save(Conference("title", "content", LocalDate.now(), LocalDate.now()))
        video = videoRepository.save(Video(conference, "url", "title", "content"))
        user = usersRepository.save(Users("user", "google", "user"))
        archive = archiveRepository.save(Archive(user, video))
    }

    @DisplayName("존재하는 정보(uid, vid)에 대한 정상 응답 테스트")
    @Test
    fun findByUserUidAndVideoVidSuccessTest() {
        var result = archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!)
        assert(result.isPresent && result.get().uidvid == archive.uidvid)
    }

    @DisplayName("존재하지 않는 정보(uid, vid)에 대한 반환값 테스트")
    @Test
    fun findByUserUidAndVideoVidFailureTest() {
        var result = archiveRepository.findByUserUidAndVideoVid(2, 2)
        assert(result.isEmpty)
    }

    @DisplayName("존재하는 정보(uid)에 대한 반환값 테스트")
    @Test
    fun findByUserUidSuccessTest() {
        var result = archiveRepository.findAllByUserUid(user.uid!!)
        assert(result.isNotEmpty())
        assert(result.size == 1)
        assert(result.get(0).video.vid == video.vid)
    }

    @DisplayName("존재하지 않는 정보(uid)에 대한 반환값 테스트")
    @Test
    fun findByUserUidFailureTest() {
        var result = archiveRepository.findAllByUserUid(2)
        assert(result.isEmpty())
    }

    @DisplayName("존재하는 정보(uid)에 대한 반환값 테스트")
    @Test
    fun findByUserUidAndLastIsTrueSuccessTest() {
        var result = archiveRepository.findByUserUidAndLastIsTrue(user.uid!!)
        assert(result.isNotEmpty())
        assert(result.size == 1)
        assert(result.get(0).video.vid == video.vid)
    }

    @DisplayName("존재하지 않는 정보(uid)에 대한 반환값 테스트")
    @Test
    fun findByUserUidAndLastIsTrueFailureTest() {
        var result = archiveRepository.findByUserUidAndLastIsTrue(2)
        assert(result.isEmpty())
    }

    @DisplayName("존재하는 정보(uid)에 대한 반환값 테스트")
    @Test
    fun findByUserUidAndLikesIsTrueSuccessTest() {
        var result = archiveRepository.findByUserUidAndLikesIsTrue(user.uid!!)
        assert(result.isEmpty())
    }

    @DisplayName("존재하지 않는 정보(uid)에 대한 반환값 테스트")
    @Test
    fun findByUserUidAndLikesIsTrueFailureTest() {
        var result = archiveRepository.findByUserUidAndLikesIsTrue(2)
        assert(result.isEmpty())
    }

    @DisplayName("존재하는 정보(uidvid)에 대한 반환값 테스트")
    @Test
    fun updateArchiveWatchRecordSuccessTest() {
        var result = archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!)
        archiveRepository.updateArchiveWatchRecord(result.get().uidvid!!, LocalTime.now())
        var updatedResult = archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!)
        assert(result.get().time != updatedResult.get().time)
    }

    @DisplayName("존재하지 않는 정보(uidvid)에 대한 반환값 테스트")
    @Test
    fun updateArchiveWatchRecordFailureTest() {
        archiveRepository.updateArchiveWatchRecord(2, LocalTime.now())
        var result = archiveRepository.findByUserUidAndVideoVid(2, 2)
        assert(result.isEmpty())
    }

    @DisplayName("존재하는 정보(uidvid)에 대한 반환값 테스트")
    @Test
    fun updateArchiveLastSuccessTest() {
        var result = archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!)
        archiveRepository.updateArchiveLast(result.get().uidvid!!, !result.get().last)
        var updatedResult = archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!)
        assertTrue(result.get().last != updatedResult.get().last)
    }

    @DisplayName("존재하지 않는 정보(uidvid)에 대한 반환값 테스트")
    @Test
    fun updateArchiveLastFailureTest() {
        archiveRepository.updateArchiveLast(2, true)
        var result = archiveRepository.findByUserUidAndVideoVid(2, 2)
        assert(result.isEmpty())
    }

    @DisplayName("존재하는 정보(uidvid)에 대한 반환값 테스트")
    @Test
    fun updateArchiveLikdSuccessTest() {
        var result = archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!)
        archiveRepository.updateArchiveLikd(result.get().uidvid!!, !result.get().likes)
        var updatedResult = archiveRepository.findByUserUidAndVideoVid(user.uid!!, video.vid!!)
        assertTrue(result.get().likes != updatedResult.get().likes)
    }

    @DisplayName("존재하지 않는 정보(uidvid)에 대한 반환값 테스트")
    @Test
    fun updateArchiveLikdFailureTest() {
        archiveRepository.updateArchiveLikd(2, true)
        var result = archiveRepository.findByUserUidAndVideoVid(2, 2)
        assert(result.isEmpty())
    }


}