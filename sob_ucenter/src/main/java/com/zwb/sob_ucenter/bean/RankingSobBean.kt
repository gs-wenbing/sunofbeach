package com.zwb.sob_ucenter.bean

/**
{
"userId":"1204736502274318336",
"avatar":"https://imgs.sunofbeaches.com/group1/M00/00/0C/rBsADV3w3l6ATs8KAAA8tUB7EHo702.png",
"nickname":"A lonely cat",
"sob":4680,
"createTime":"2021-04-14T09:36:37.000+0000",
"vip":true
}
 */
data class RankingSobBean(
    val avatar: String,
    val createTime: String,
    val nickname: String,
    val sob: Int,
    val userId: String,
    val vip: Boolean
)