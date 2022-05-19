package com.zwb.sob_ucenter.bean

/**
{
"_id":"976401919897501696",
"title":"每日登录奖励",
"publishTime":"2022-05-18 08:32",
"content":"<a href='https://mp.sunofbeach.net/#/index/setting/sobRecord' style='color:#e6a23c'>每日登录奖励2个sunof币</a> 感谢有你！",
"state":"1",
"exId":null,
"exType":"sobTrade",
"userId":"1520570267082952706"
}
 */
data class MsgSystemBean(
    val _id: String,
    val content: String,
    val exId: Any,
    val exType: String,
    val publishTime: String,
    val state: String,
    val title: String,
    val userId: String
)