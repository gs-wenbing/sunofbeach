package com.zwb.sob_moyu.bean

import com.zwb.lib_common.bean.ICategoryItem

data class TopicIndexBean(
    var id: String,
    var topicName: String,
): ICategoryItem {
    override fun getCategoryId(): String {
        return id
    }

    override fun getName(): String {
        return topicName
    }
}
