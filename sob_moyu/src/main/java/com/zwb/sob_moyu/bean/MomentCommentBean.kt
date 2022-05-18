package com.zwb.sob_moyu.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_common.constant.Constants

data class MomentCommentBean(
    val avatar: String,
    val company: String?,
    val content: String,
    val createTime: String,
    val id: String,
    val momentId: String,
    val nickname: String,
    val position: String?,
    val subComments: List<MomentSubComment>,
    val thumbUp: Int,
    val thumbUpList: List<String>,
    val userId: String,
    val vip: Boolean
): MultiItemEntity{
    override fun getItemType(): Int {
        return Constants.MultiItemType.TYPE_COMMENT
    }

}

