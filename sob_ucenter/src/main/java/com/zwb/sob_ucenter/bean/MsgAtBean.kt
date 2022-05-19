package com.zwb.sob_ucenter.bean

/**
{
"_id":"1526826044714127361",
"beUid":"1520570267082952706",
"beNickname":null,
"uid":"1139423796017500160",
"nickname":"工头断点",
"url":"/m/1526772230179000322#1526826044714127361",
"content":"完成度很高，哈哈，强",
"type":"moment",
"hasRead":"0",
"publishTime":"2022-05-18 15:24",
"timeText":null,
"avatar":"https://imgs.sunofbeaches.com/group1/M00/00/04/rBsADV2YuTKABc4DAABfJHgYqP8031.png",
"exId":"1526772230179000322"
}
 */
data class MsgAtBean(
      var _id: String,
    var avatar: String,
    var beNickname: Any,
    var beUid: String,
    var content: String,
    var exId: String,
    var hasRead: String,
    var nickname: String,
    var publishTime: String,
    var timeText: String?,
    var type: String,
     var uid: String,
    var url: String
):MsgBean()