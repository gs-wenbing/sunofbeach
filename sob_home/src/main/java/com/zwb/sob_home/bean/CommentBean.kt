package com.zwb.sob_home.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_common.bean.SubCommentBean
import com.zwb.lib_common.constant.Constants

/**
 * 文章评论
 */
data class CommentBean(
    val _id: String,
    val articleId: String,
    val avatar: String?,
    val commentContent: String?,
    val isTop: String?,
    val nickname: String,
    val parentId: String,
    val publishTime: String?,
    val role: String?,
    val subComments: List<SubCommentBean>,
    val userId: String,
    val vip: Boolean
) : MultiItemEntity {
    override fun getItemType(): Int {
        return Constants.MultiItemType.TYPE_COMMENT
    }
}
