package com.zwb.sob_home.bean

data class CategoryBean(
    var id: String,
    var categoryName: String,
    var order: Int,
    var description: String="",
    var createTime: String="",
    var pyName: String=""
)
