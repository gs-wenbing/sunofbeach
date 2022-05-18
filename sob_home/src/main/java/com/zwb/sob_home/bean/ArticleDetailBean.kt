package com.zwb.sob_home.bean

data class ArticleDetailBean(
    var id: String,
    var title: String,
    var userId: String,
    var categoryId: String,
    var categoryName: String,
    var contentType: Int,
    var content: String,
    var createTime: String,
    var labels: List<String>,
    var viewCount: Int,
    var thumbUp: Int,
    var recommend: Int,
    var covers: List<String>,
    var articleType: Int,
    var avatar: String,
    var isVip: Int,
    var nickname: String,
    var isTop: Int,
    var isComment: Int,
    var state: Int
)