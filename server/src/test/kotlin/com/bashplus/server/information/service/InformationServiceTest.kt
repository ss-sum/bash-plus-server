package com.bashplus.server.information.service

import com.bashplus.server.host.domain.Conference
import com.bashplus.server.host.domain.ConferenceHost
import com.bashplus.server.host.domain.Host
import com.bashplus.server.host.repository.ConferenceHostRepository
import com.bashplus.server.host.repository.ConferenceRepository
import com.bashplus.server.host.repository.HostRepository
import com.bashplus.server.information.domain.Category
import com.bashplus.server.information.repository.CategoryRepository
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class InformationServiceTest {
    @Autowired
    private lateinit var informationService: InformationService

    @Autowired
    private lateinit var categoryRepository: CategoryRepository

    @Autowired
    private lateinit var hostRepository: HostRepository

    @Autowired
    private lateinit var conferenceRepository: ConferenceRepository

    @Autowired
    private lateinit var conferenceHostRepository: ConferenceHostRepository

    private lateinit var conference: Conference
    private lateinit var category: Category
    private lateinit var host: Host

    @BeforeAll
    fun setUp() {
        category = categoryRepository.save(Category("spring", 1))
        host = hostRepository.save(Host("company", "channel"))
        conference = conferenceRepository.save(Conference("test1", "content", LocalDate.now(), LocalDate.now()))
        conferenceHostRepository.save(ConferenceHost(conference, host))
    }

    @AfterAll
    fun tearDown() {
        conferenceHostRepository.deleteAll()
        conferenceRepository.deleteAll()
        hostRepository.deleteAll()
        categoryRepository.deleteAll()
    }

    @DisplayName("컨퍼런스 호스트 목록을 받아오는 함수 정상 동작에 대한 테스트")
    @Test
    fun getConferenceHostsTest() {
        val result = informationService.getConferenceHosts()
        assertEquals(1, result.size)
        assertEquals(result.get(0).hid, host.hid)
    }

    @DisplayName("카테고리 리스트를 받아오는 함수 정상 동작에 대한 테스트")
    @Test
    fun getCategoriesTest() {
        val result = informationService.getCategories()
        assertEquals(1, result.size)
        assertEquals(result.get(0).tid, category.tid)
    }
}