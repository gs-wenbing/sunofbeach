package com.zwb.sob_ucenter.bean

/**
{
"_id":"977154066100518912",
"bUid":"1520570267082952706",
"articleId":"1526740548772171777",
"nickname":"工头断点",
"avatar":"https://imgs.sunofbeaches.com/group1/M00/00/04/rBsADV2YuTKABc4DAABfJHgYqP8031.png",
"uid":"1139423796017500160",
"hasRead":"1",
"title":"MVVM+Kotlin+组件化 开发的阳光沙滩APP",
"content":"客户端越来越多了，哈哈\uD83D\uDE04",
"createTime":"2022-05-20 10:21",
"timeText":null
}
 */
data class MsgArticleBean(
    val _id: String,
    val articleId: String,
    val avatar: String,
    val bUid: String,
    val content: String,
    val createTime: String,
    val hasRead: String,
    val nickname: String,
    val timeText: Any,
    val title: String,
    val uid: String
):MsgBean()