package com.zwb.sob_ucenter.bean

/**
{
"id":"1525271754379890689",
"userId":"1520570267082952706",
"sob":2,
"content":"每日登录奖励",
"createTime":"2022-05-14T00:28:19.000+0000",
"tax":0,
"updateTime":"2022-05-14T00:28:19.000+0000"
}
 */
data class SobBean(
    val content: String,
    val createTime: String,
    val id: String,
    val sob: Int,
    val tax: Int,
    val updateTime: String,
    val userId: String
)