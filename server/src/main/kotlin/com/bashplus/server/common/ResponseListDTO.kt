package com.bashplus.server.common

class ResponseListDTO<T>(message: List<T>, pageNum: Int = 0, pageSize: Int = 0, totalCount: Long = 0) {
    private val totalCount: Long = totalCount
    private val pageNum: Int = pageNum
    private val pageSize: Int = pageSize
    private val message: List<T> = message
    fun getTotalCount(): Long {
        return totalCount
    }

    fun getPageNum(): Int {
        return pageNum
    }

    fun getPageSize(): Int {
        return pageSize
    }

    fun getMessage(): List<T> {
        return message
    }
}