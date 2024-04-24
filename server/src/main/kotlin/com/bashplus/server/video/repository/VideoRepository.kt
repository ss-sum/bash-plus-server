package com.bashplus.server.video.repository

import com.bashplus.server.video.domain.Video
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.util.*

@Repository
open interface VideoRepository : JpaRepository<Video, String> {
    open fun findByVid(vid: Long): Optional<Video>
    open fun findAllByOrderByConferenceStartAtTimeDesc(pageable: Pageable): Page<Video>
    open fun findAllByOrderByConferenceStartAtTimeAsc(pageable: Pageable): Page<Video>

    @Query("SELECT v from Video v LEFT JOIN Archive a on a.video.vid = v.vid group by v.vid order by count(a.likes) desc")
    open fun findAllByLikeDesc(pageable: Pageable): Page<Video>

    @Query("SELECT v from Video v LEFT JOIN Archive a on a.video.vid = v.vid group by v.vid order by count(a.likes) asc")
    open fun findAllByLikeAsc(pageable: Pageable): Page<Video>
    open fun findAllByConferenceTitleIsLikeIgnoreCaseOrderByConferenceStartAtTimeDesc(title: String, pageable: Pageable): Page<Video>
    open fun findAllByConferenceTitleIsLikeIgnoreCaseOrderByConferenceStartAtTimeAsc(title: String, pageable: Pageable): Page<Video>

    @Query("SELECT v FROM Video v LEFT JOIN Archive a on a.video.vid = v.vid WHERE LOWER(v.conference.title) LIKE LOWER(:title) group by v.vid order by count(a.likes) desc")
    open fun findAllByConferenceTitleIsLikeIgnoreCaseOrderByLike(title: String, pageable: Pageable): Page<Video>

    @Query("SELECT v FROM Video v LEFT JOIN Archive a on a.video.vid = v.vid WHERE LOWER(v.conference.title) LIKE LOWER(:title) group by v.vid order by count(a.likes) asc")
    open fun findAllByConferenceTitleIsLikeIgnoreCaseOrderByLikeAsc(title: String, pageable: Pageable): Page<Video>

    open fun findAllByTitleIsLikeIgnoreCaseOrderByConferenceStartAtTimeDesc(title: String, pageable: Pageable): Page<Video>
    open fun findAllByTitleIsLikeIgnoreCaseOrderByConferenceStartAtTimeAsc(title: String, pageable: Pageable): Page<Video>

    @Query("SELECT v FROM Video v LEFT JOIN Archive a on a.video.vid = v.vid WHERE LOWER(v.title) LIKE LOWER(:title) group by v.vid order by count(a.likes) desc")
    open fun findAllByTitleIsLikeIgnoreCaseOrderByLike(title: String, pageable: Pageable): Page<Video>

    @Query("SELECT v FROM Video v LEFT JOIN Archive a on a.video.vid = v.vid WHERE LOWER(v.title) LIKE LOWER(:title) group by v.vid order by count(a.likes) asc")
    open fun findAllByTitleIsLikeIgnoreCaseOrderByLikeAsc(title: String, pageable: Pageable): Page<Video>

    @Query("SELECT v FROM Video v WHERE v.conference.coid IN (SELECT ch.conference.coid FROM ConferenceHost ch JOIN Host h ON ch.host.hid = h.hid WHERE LOWER(h.company) like LOWER(:host)) order by v.conference.startAtTime desc")
    open fun findAllByConferenceHost(host: String, pageable: Pageable): Page<Video>

    @Query("SELECT v FROM Video v WHERE v.conference.coid IN (SELECT ch.conference.coid FROM ConferenceHost ch JOIN Host h ON ch.host.hid = h.hid WHERE LOWER(h.company) like LOWER(:host)) order by v.conference.startAtTime asc")
    open fun findAllByConferenceHostAsc(host: String, pageable: Pageable): Page<Video>

    @Query("SELECT v FROM Video v LEFT JOIN Archive a on a.video.vid = v.vid WHERE v.conference.coid IN (SELECT ch.conference.coid FROM ConferenceHost ch JOIN Host h ON ch.host.hid = h.hid WHERE LOWER(h.company) like LOWER(:host)) group by v.vid order by count(a.likes) desc")
    open fun findAllByConferenceHostOrderByLike(host: String, pageable: Pageable): Page<Video>

    @Query("SELECT v FROM Video v LEFT JOIN Archive a on a.video.vid = v.vid WHERE v.conference.coid IN (SELECT ch.conference.coid FROM ConferenceHost ch JOIN Host h ON ch.host.hid = h.hid WHERE LOWER(h.company) like LOWER(:host)) group by v.vid order by count(a.likes) asc")
    open fun findAllByConferenceHostOrderByLikeAsc(host: String, pageable: Pageable): Page<Video>


    @Query("SELECT v from Video v WHERE v.vid IN (SELECT vt.video.vid FROM VideoTag vt JOIN Category t ON vt.category.tid = t.tid WHERE LOWER(t.category) like LOWER(:category)) order by v.conference.startAtTime desc")
    open fun findAllByVideoCategory(category: String, pageable: Pageable): Page<Video>

    @Query("SELECT v from Video v WHERE v.vid IN (SELECT vt.video.vid FROM VideoTag vt JOIN Category t ON vt.category.tid = t.tid WHERE LOWER(t.category) like LOWER(:category)) order by v.conference.startAtTime asc")
    open fun findAllByVideoCategoryAsc(category: String, pageable: Pageable): Page<Video>

    @Query("SELECT v from Video v LEFT JOIN Archive a on a.video.vid = v.vid WHERE v.vid IN (SELECT vt.video.vid FROM VideoTag vt JOIN Category t ON vt.category.tid = t.tid WHERE LOWER(t.category) like LOWER(:category)) group by v.vid order by count(a.likes) desc")
    open fun findAllByVideoCategoryOrderByLike(category: String, pageable: Pageable): Page<Video>

    @Query("SELECT v from Video v LEFT JOIN Archive a on a.video.vid = v.vid WHERE v.vid IN (SELECT vt.video.vid FROM VideoTag vt JOIN Category t ON vt.category.tid = t.tid WHERE LOWER(t.category) like LOWER(:category)) group by v.vid order by count(a.likes) asc")
    open fun findAllByVideoCategoryOrderByLikeAsc(category: String, pageable: Pageable): Page<Video>
}