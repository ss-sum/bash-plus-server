package com.bashplus.server.users.repository

import com.bashplus.server.users.domain.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
open interface UsersRepository : JpaRepository<Users, String> {
    override public fun findById(id: String): Optional<Users>
}