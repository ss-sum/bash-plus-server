package com.bashplus.server.host.repository

import com.bashplus.server.host.domain.Conference
import com.bashplus.server.host.domain.ConferenceHost
import com.bashplus.server.host.domain.Host
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
class ConferenceHostRepositoryTest {
    @Autowired
    private lateinit var conferenceHostRepository: ConferenceHostRepository

    @Autowired
    private lateinit var hostRepository: HostRepository

    @Autowired
    private lateinit var conferenceRepository: ConferenceRepository

    private lateinit var conference: Conference
    private lateinit var host: Host

    @BeforeAll
    fun setUp() {
        host = hostRepository.save(Host("company", "channel", "content"))
        conference = conferenceRepository.save(Conference("test1", "content", LocalDate.now(), LocalDate.now()))
        conferenceHostRepository.save(ConferenceHost(conference, host))
    }

    @DisplayName("존재하는 컨퍼런스 정보(cid)값에 대한 정상 응답 테스트")
    @Test
    fun findByConferenceCoidSuccessTest() {
        var result = conferenceHostRepository.findAllByConferenceCoid(conference.coid!!)
        assert(result.size == 1)
        assert(result.get(0).conference.title.equals(conference.title))
    }

    @DisplayName("존재하지 않는 컨퍼런스 정보(cid)값에 대한 반환값 테스트")
    @ParameterizedTest
    @ValueSource(longs = [2])
    fun findByConferenceCoidFailureTest(input: Long) {
        var result = conferenceHostRepository.findAllByConferenceCoid(input)
        assert(result.isEmpty())
    }

}