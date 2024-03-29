package com.bashplus.server.users.repository

import com.bashplus.server.users.domain.Interest
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
open interface InterestRepository : JpaRepository<Interest, String> {
    public fun findAllByUsersUid(uid: Long): List<Interest>
}