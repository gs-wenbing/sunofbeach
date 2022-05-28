package com.zwb.sob_moyu.bean

/**
 * content为评论内容，
 * momentId为动态Id，
 * targetUserId是被评论内容的用户Id，
 * commentId为被评论内容的Id
 */
data class SubCommentInputBean(
    val commentId: String,
    val content: String,
    val momentId: String,
    val targetUserId: String
)