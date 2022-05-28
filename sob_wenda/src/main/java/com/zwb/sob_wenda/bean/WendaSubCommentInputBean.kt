package com.zwb.sob_wenda.bean

/**
 * parentId 被评论的答案ID
beNickname 被评论人的昵称
beUid 被评论人的用户ID
wendaId 问题的ID
 */
data class WendaSubCommentInputBean(
    val beNickname: String,
    val beUid: String,
    val content: String,
    val parentId: String,
    val wendaId: String
)