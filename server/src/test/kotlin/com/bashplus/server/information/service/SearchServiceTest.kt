package com.bashplus.server.information.service

import com.bashplus.server.host.domain.Conference
import com.bashplus.server.host.domain.ConferenceHost
import com.bashplus.server.host.domain.Host
import com.bashplus.server.host.repository.ConferenceHostRepository
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.host.repository.HostRepository
import com.bashplus.server.information.domain.Category
import com.bashplus.server.information.repository.CategoryRepository
import com.bashplus.server.video.domain.Video
import com.bashplus.server.video.repository.VideoRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class SearchServiceTest {
    @Autowired
    private lateinit var searchService: SearchService

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var hostRepository: HostRepository

    @Autowired
    private lateinit var conferenceRepository: ConferenceRepository

    @Autowired
    private lateinit var conferenceHostRepository: ConferenceHostRepository

    @Autowired
    private lateinit var videoRepository: VideoRepository
    private lateinit var conference: Conference
    private lateinit var category: Category
    private lateinit var host: Host
    private lateinit var video: Video

    @BeforeAll
    fun setUp() {
        category = categoryRepository.save(Category("spring", 1))
        host = hostRepository.save(Host("company", "channel"))
        conference = conferenceRepository.save(Conference("test1", "content", LocalDate.now(), LocalDate.now()))
        conferenceHostRepository.save(ConferenceHost(conference, host))
        video = videoRepository.save(Video(conference, "url", "test1", "content"))

    }

    @AfterAll
    fun tearDown() {
        conferenceHostRepository.deleteAll()
        videoRepository.deleteAll()
        conferenceRepository.deleteAll()
        hostRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    @DisplayName("비디오 목록을 받아오는 함수 정상 동작에 대한 테스트")
    @Test
    fun getVideosTest() {
        val result = searchService.getAllVideos()
        assertEquals(1, result.size)
    }

    @DisplayName("호스트 검색 결과를 받아오는 함수 정상 동작에 대한 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["company", "Company", "Com", "comPany", "c", "co"])
    fun searchHostTest(name: String) {
        val result = searchService.getHostSearchResult(name)
        assertEquals(1, result.size)
        assert(result[0].hid == host.hid)
    }

    @DisplayName("카테고리 검색 결과를 받아오는 함수 정상 동작에 대한 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["spring", "Spring", "SPRING", "spRinG", "s", "sp"])
    fun searchCategoryTest(category: String) {
        val result = searchService.getCategorySearchResult(category)
        assertEquals(1, result.size)
        assert(result[0].tid == this.category.tid)
    }

    @DisplayName("비디오 검색 결과를 받아오는 함수 정상 동작에 대한 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["test1", "Test1", "TEST1", "teSt1", "t", "te"])
    fun searchVideoTest(title: String) {
        val result = searchService.getVideoSearchResult(title)
        assertEquals(1, result.size)
        assert(result[0].title == video.title)
    }

}