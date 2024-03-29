package com.bashplus.server.host.repository

import com.bashplus.server.host.domain.Host
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
open interface HostRepository : JpaRepository<Host, String> {
    open fun findByHid(hid: Long): Optional<Host>
    open fun findAllByCompanyIsLike(company: String): List<Host>
}