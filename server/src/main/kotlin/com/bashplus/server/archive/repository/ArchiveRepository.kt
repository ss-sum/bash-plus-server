package com.bashplus.server.archive.repository

import com.bashplus.server.archive.domain.Archive
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import java.time.LocalTime
import java.util.*

@Repository
open interface ArchiveRepository : JpaRepository<Archive, String> {
    open fun findByUserUidAndVideoVid(uid: Long, vid: Long): Optional<Archive>
    open fun findAllByUserUid(uid: Long): List<Archive>
    open fun findByUserUidAndLastIsTrue(uid: Long): List<Archive>
    open fun findByUserUidAndLikesIsTrue(uid: Long): List<Archive>

    @Transactional
    @Modifying
    @Query("UPDATE Archive a SET a.time = :time WHERE a.uidvid= :id")
    open fun updateArchiveWatchRecord(@Param("id") uidvid: Long, @Param("time") time: LocalTime)

    @Transactional
    @Modifying
    @Query("UPDATE Archive a SET a.last = :last WHERE a.uidvid= :id")
    open fun updateArchiveLast(@Param("id") uidvid: Long, @Param("last") last: Boolean)


    @Transactional
    @Modifying
    @Query("UPDATE Archive a SET a.likes = :like WHERE a.uidvid= :id")
    open fun updateArchiveLikd(@Param("id") uidvid: Long, @Param("like") like: Boolean)
}