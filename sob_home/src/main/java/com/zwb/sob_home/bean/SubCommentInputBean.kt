package com.zwb.sob_home.bean

/**
 * articleId，文章Id
parentId，被回复评论的Id
beUid，被评论用户的Id
beNickName，被评论用户的昵称
content,评论内容
 */
data class SubCommentInputBean(
    val articleId: String,
    val beNickname: String,
    val beUid: String,
    val content: String,
    val parentId: String
)