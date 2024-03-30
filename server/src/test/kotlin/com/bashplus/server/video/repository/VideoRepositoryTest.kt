package com.bashplus.server.video.repository

import com.bashplus.server.host.domain.Conference
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.video.domain.Video
import org.junit.jupiter.api.*
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
internal class VideoRepositoryTest {
    @Autowired
    private lateinit var videoRepository: VideoRepository

    @Autowired
    private lateinit var conferenceRepository: ConferenceRepository
    private lateinit var video: Video
    private lateinit var conference: Conference


    @BeforeAll
    fun setUp() {
        conference = conferenceRepository.save(Conference("title", "description", LocalDate.now(), LocalDate.now()))
        video = videoRepository.save(Video(conference, "description", "url", "thumbnail"))
    }

    @AfterAll
    fun tearDown() {
        videoRepository.delete(video)
        conferenceRepository.delete(conference)
    }

    @DisplayName("존재하는 비디오 정보(vid)값에 대한 정상 응답 테스트")
    @Test
    fun findByVidSuccessTest() {
        var result = videoRepository.findByVid(video.vid!!)
        assert(result.isPresent)
        assert(result.get().vid == video.vid)
        assert(result.get().title == video.title)
    }

    @DisplayName("존재하지 않는 비디오 정보(vid)값에 대한 반환값 테스트")
    @ParameterizedTest
    @ValueSource(longs = [2])
    fun findByVidFailureTest(vid: Long) {
        var result = videoRepository.findByVid(vid)
        assert(result.isEmpty)
    }

    @DisplayName("존재하는 비디오 정보(title)값에 대한 반환값 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["t", "tit", "Tit", "titl", "title", "tItLe"])
    fun findAllByTitleIsLikeSuccessTest(text: String) {
        var result = videoRepository.findAllByTitleIsLike(text)
        assert(result.size == 1)
        assert(result.contains(video))
    }

    @DisplayName("존재하지 않는 비디오 정보(title)값에 대한 반환값 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["none", "t;t"])
    fun findAllByTitleIsLikeFailureTest(text: String) {
        var result = videoRepository.findAllByTitleIsLike(text)
        assert(result.isEmpty())
    }
}