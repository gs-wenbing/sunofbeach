package com.zwb.lib_base.bean

class ListData<E>(
    var list: List<E>,
    var total: Int,
    var pageSize: Int,
    var currentPage: Int,
    var hasNext: Boolean,
    var hasPre: Boolean,
    var totalPage: Int
)