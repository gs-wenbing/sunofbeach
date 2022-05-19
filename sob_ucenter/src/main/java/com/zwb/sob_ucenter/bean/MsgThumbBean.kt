package com.zwb.sob_ucenter.bean

/**
{
"_id":"976452390540541952",
"beUid":"1520570267082952706",
"title":"上班之余，做了个阳光沙滩app请大家多多指教",
"url":"/m/1526772230179000322",
"nickname":"阿肥",
"avatar":"https://images.sunofbeaches.com/content/2022_01_04/927947264050069504.png",
"uid":"1382711465131241472",
"thumbTime":"2022-05-18 11:53",
"timeText":"1小时前",
"hasRead":"1"
}

 */
data class MsgThumbBean(
    val _id: String,
    val avatar: String,
    val beUid: String,
    val hasRead: String,
    val nickname: String,
    val thumbTime: String,
    val timeText: String,
    val title: String,
    val uid: String,
    val url: String
):MsgBean()