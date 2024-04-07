package com.bashplus.server.host.repository

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

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class HostRepositoryTest {
    @Autowired
    private lateinit var hostRepository: HostRepository
    private lateinit var host: Host

    @BeforeAll
    fun setUp() {
        host = hostRepository.save(Host("company", "channel", "content"))
    }

    @DisplayName("존재하는 호스트 정보(hid)값에 대한 정상 응답 테스트")
    @Test
    fun findByHidSuccessTest() {
        var result = hostRepository.findByHid(host.hid!!)
        assert(result.isPresent)
        assert(result.get().company.equals(host.company))
    }

    @DisplayName("존재하지 않는 호스트 정보(hid)값에 대한 반환값 테스트")
    @ParameterizedTest
    @ValueSource(longs = [2])
    fun findByHidFailureTest(input: Long) {
        var result = hostRepository.findByHid(input)
        assert(result.isEmpty)
    }

    @DisplayName("존재하는 호스트 정보(company)값에 대한 반환값 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["company", "Com", "ComP", "comP"])
    fun findByCompanySuccessTest(text: String) {
        var result = hostRepository.findAllByCompanyIsLike(text)
        assert(result.size == 1)
        assert(result.get(0).company.equals(host.company))
    }

    @DisplayName("존재하지 않는 호스트 정보(company)값에 대한 반환값 테스트")
    @ParameterizedTest
    @ValueSource(strings = ["company2", "Com2", "ComP2", "comP2"])
    fun findByCompanyFailureTest(text: String) {
        var result = hostRepository.findAllByCompanyIsLike(text)
        assert(result.isEmpty())
    }

}