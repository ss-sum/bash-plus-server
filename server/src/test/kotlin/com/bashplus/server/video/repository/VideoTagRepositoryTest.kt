package com.bashplus.server.video.repository

import com.bashplus.server.host.domain.Conference
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.information.domain.Category
import com.bashplus.server.information.repository.CategoryRepository
import com.bashplus.server.video.domain.Video
import com.bashplus.server.video.domain.VideoTag
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
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
internal class VideoTagRepositoryTest {
    @Autowired
    private lateinit var videoTagRepository: VideoTagRepository

    @Autowired
    private lateinit var videoRepository: VideoRepository

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var conferenceRepository: ConferenceRepository
    private lateinit var video: Video
    private lateinit var category: Category
    private lateinit var conference: Conference

    @BeforeAll
    fun setUp() {
        category = categoryRepository.save(Category("spring", 1))
        conference = conferenceRepository.save(Conference("title", "description", LocalDate.now(), LocalDate.now()))
        video = videoRepository.save(Video(conference, "description", "url", "thumbnail"))
        videoTagRepository.save(VideoTag(video, category))
    }

    @DisplayName("존재하는 비디오 태그 정보(vid)값에 대한 정상 응답 테스트")
    @Test
    fun findByVidSuccessTest() {
        var result = videoTagRepository.findTagByVid(video.vid!!)
        assert(result.size == 1)
        assert(result.get(0).equals(category.category))
    }

    @DisplayName("존재하지 않는 비디오 태그 정보(vid)값에 대한 반환값 테스트")
    @ParameterizedTest
    @ValueSource(longs = [2])
    fun findByVidFailureTest(vid: Long) {
        var result = videoTagRepository.findTagByVid(vid)
        assert(result.isEmpty())
    }
}