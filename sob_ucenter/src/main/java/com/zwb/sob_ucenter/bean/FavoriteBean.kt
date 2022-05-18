package com.zwb.sob_ucenter.bean

/**
{
"_id":"975053539036364800",
"userId":"1520570267082952706",
"avatar":null,
"userName":null,
"collectionId":"972105680582344704",
"title":"阳光沙滩Android客户端文章优化",
"url":"https://www.sunofbeach.net/a/1524764818681106434",
"type":"0",
"addTime":"2022-05-14 15:14",
"cover":""
}
 */
data class FavoriteBean(
    val _id: String,
    val addTime: String,
    val avatar: String?,
    val collectionId: String,
    val cover: String,
    val title: String,
    val type: String,
    val url: String,
    val userId: String,
    val userName: String?
)