package com.bashplus.server.host.repository

import com.bashplus.server.host.domain.ConferenceHost
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
open interface ConferenceHostRepository : JpaRepository<ConferenceHost, String> {
    open fun findAllByConferenceCoid(coid: Long): List<ConferenceHost>
    open fun findAllByHostHid(hid: Long): List<ConferenceHost>
}