package com.zwb.lib_common.bean

data class CollectInputBean(
    val collectionId: String,
    val cover: String = "",
    val title: String,
    val type: String = "0",
    val url: String
)