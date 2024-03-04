package com.bashplus.server.host.repository

import com.bashplus.server.host.domain.Conference
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
open interface ConferenceRepository : JpaRepository<Conference, String> {
}