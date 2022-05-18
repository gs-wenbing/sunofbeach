package com.zwb.lib_common.bean

/**
 * "userId": "1153952789488054272",
 * "nickname": "拉大锯",
 * "avatar": "https://imgs.sunofbeaches.com/group1/M00/00/07/rBsADV22ZymAV8BwAABVL9XtNSU926.png",
 * "position": "首席打杂工程师",
 * "company": "阳光沙滩",
 * "sign": "三界之内没有我解不了的bug",
 * "area": "广东省/深圳市/福田区",
 * "vip": false
 */
data class UserBean(
    var userId: String,
    var avatar: String? = null,
    var nickname: String? = null,
    var position: String? = null,
    var company: String? = null,
    var sign: String? = null,
    var area: String? = null,
    var vip: Boolean
)
