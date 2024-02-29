package com.bashplus.server.users.repository

import com.bashplus.server.users.domain.Users
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Repository
open interface UsersRepository : JpaRepository<Users, String> {
    public fun findByIdAndType(id: String, type: String): Optional<Users>

    public fun findByUid(uid: Long): Optional<Users>

    @Transactional
    @Modifying
    @Query("UPDATE Users u SET u.access = :accessToken, u.refresh = :refreshToken WHERE u.id = :id and u.type = :type")
    public fun updateToken(@Param("id") id: String, @Param("type") type: String, @Param("accessToken") accessToken: String, @Param("refreshToken") refreshToken: String)
}