package com.zwb.sob_wenda.bean

/**
{
"avatar":"https://imgs.sunofbeaches.com/group1/M00/00/04/rBsADV2YuTKABc4DAABfJHgYqP8031.png",
"nickname":"工头断点",
"count":5,
"userId":"1139423796017500160",
"vip":true
}
 *
 */
data class WendaRankingBean(
    val avatar: String,
    val count: Int,
    val nickname: String,
    val userId: String,
    val vip: Boolean
)