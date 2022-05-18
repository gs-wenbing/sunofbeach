package com.zwb.lib_base.bean

data class PageViewData<T>(
    val content: List<T>,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: SortX,
    val totalElements: Int,
    val totalPages: Int
)

data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val sort: Sort,
    val unpaged: Boolean
)

data class SortX(
    val sorted: Boolean,
    val unsorted: Boolean
)

data class Sort(
    val sorted: Boolean,
    val unsorted: Boolean
)