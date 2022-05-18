package com.zwb.lib_common.bean

data class TokenBean(
    var id: String,
    var roles: String? = null,
    var avatar: String? = null,
    var nickname: String? = null,
    var lev: Int,
    var isVip: String? = null,
    var token: String? = null,
    var fansCount: Int? = null,
    var followCount: Int? = null,
)
