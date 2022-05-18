package com.zwb.sob_wenda.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_common.constant.Constants

data class WendaBean(
    val answerCount: Int?,
    val avatar: String?,
    val categoryId: String?,
    val categoryName: String?,
    val createTime: String?,
    val id: String,
    val isResolve: String?,
    val isVip: String?,
    val label: String?,
    val labels: List<String>,
    val nickname: String?,
    val sob: Int?,
    val thumbUp: Int?,
    val title: String?,
    val userId: String,
    val viewCount: Int?
): MultiItemEntity {
    override fun getItemType(): Int {
        return Constants.MultiItemType.TYPE_RECOMMEND
    }

}