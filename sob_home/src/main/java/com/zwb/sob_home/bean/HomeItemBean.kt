package com.zwb.sob_home.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

data class HomeItemBean(
    var covers: List<String> = mutableListOf(),
    var title: String = "",
    var createTime: String = "",
    var thumbUp: Int = 0,
    var viewCount: Int = 0,
    var type: Int = 0,
    var nickName: String = "",
    var avatar: String = "",
    var id: String = "",
    var userId: String = "",
    var isVip: Boolean = false
) : MultiItemEntity {

    override fun getItemType(): Int {
        return if (this.covers.size > 1) 2 else 1
    }
}