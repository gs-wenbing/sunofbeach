package com.zwb.sob_shop.bean

data class TbkTpwdResponse(
    val tbk_tpwd_create_response: TbkTpwdCreateResponseX
)

data class TbkTpwdCreateResponseX(
    val `data`: Data,
    val request_id: String
)

data class Data(
    val model: String
)