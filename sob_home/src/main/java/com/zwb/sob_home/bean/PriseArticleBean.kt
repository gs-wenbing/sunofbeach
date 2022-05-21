package com.zwb.sob_home.bean

/**
 * {
"userId":"1284274686481473536",
"avatar":"https://images.sunofbeaches.com/content/2021_07_15/865370265700270080.png",
"nickname":"YanLQ",
"sob":8,
"createTime":"2022-05-14T07:44:44.000+0000",
"vip":true
}
 */
data class PriseArticleBean(
    val avatar: String,
    val createTime: String,
    val nickname: String,
    val sob: Int,
    val userId: String,
    val vip: Boolean
)