package com.zwb.sob_ucenter.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_common.bean.SubCommentBean

/**
{
    "wendaTitle":"在安卓10.0上显示服务器上的LsitView图片加载不出",
    "wendaComment":{
        "_id":"1261622286318886912",
        "wendaId":"1261614701222092800",
        "thumbUp":2,
        "bestAs":"0",
        "content":"<p>你打log看看图片的url是多少，直接用浏览器访问看看有结果没有呗</p><p>我感觉可能是图片尺寸问题</p>",
        "nickname":"lxxxx9",
        "avatar":"https://imgs.sunofbeaches.com/group1/M00/00/28/rBsADV8qufOAVRZDAABZjTaGYnc500.png",
        "uid":"1247069679944470528",
        "subCommentCount":0,
        "publishTime":"2020-05-16 19:39",
        "publishTimeText":null,
        "thumbUps":[
            "1153952789488054272",
            "1162543794353192960"
        ],
        "wendaSubComments":[

        ],
        "vip":false
    }
}
 */
data class UserWendaBean(
    val wendaComment: WendaComment,
    val wendaTitle: String
): MultiItemEntity {
    override fun getItemType(): Int {
        return 3
    }
}

data class WendaComment(
    val _id: String,
    val avatar: String,
    val bestAs: String?,
    val content: String,
    val nickname: String,
    val publishTime: String?,
    val publishTimeText: String?,
    val subCommentCount: Int,
    val thumbUp: Int?,
    val thumbUps: List<String>,
    val uid: String,
    val vip: Boolean,
    val wendaId: String,
    val wendaSubComments: List<SubCommentBean>? = mutableListOf()
)