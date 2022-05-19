package com.zwb.sob_ucenter.bean

/**
 * {
"_id":"1526782167722496001",
"bUid":"1520570267082952706",
"momentId":"1526772230179000322",
"nickname":"阿肥",
"avatar":"https://images.sunofbeaches.com/content/2022_01_04/927947264050069504.png",
"uid":"1382711465131241472",
"hasRead":"1",
"title":"上班之余，做了个阳光沙滩app<img class=\"emoji\" src=\"https://cdn.sunofbeaches....",
"content":"偷偷干大事",
"createTime":"2022-05-18 12:30",
"timeText":null
}
 */
data class MsgMomentBean(
     var _id: String,
    var avatar: String,
    var bUid: String,
    var content: String,
    var createTime: String,
    var hasRead: String,
    var momentId: String,
    var nickname: String,
    var timeText: String?,
    var title: String,
     var uid: String
):MsgBean()