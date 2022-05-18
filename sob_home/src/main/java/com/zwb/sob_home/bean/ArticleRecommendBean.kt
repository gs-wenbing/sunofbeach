package com.zwb.sob_home.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_common.constant.Constants

/**
 * 文章详情 相关推荐
 */
data class ArticleRecommendBean(
    var id: String,
    var title: String,
    var nickname: String,
    var avatar: String,
    var userId: String,
    var createTime: String,
    var labels: List<String>,
    var viewCount: Int,
    var covers: List<String>,
    var vip: Boolean,
    var thumbUp: Int
) : MultiItemEntity {
    override fun getItemType(): Int {
        return Constants.MultiItemType.TYPE_RECOMMEND
    }
}
