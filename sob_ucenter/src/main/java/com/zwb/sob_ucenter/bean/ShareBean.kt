package com.zwb.sob_ucenter.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
{
    "id":"973174643173097472",
    "title":"WebStorm历史版本",
    "url":"https://www.jetbrains.com/webstorm/download/other.html",
    "labels":[
    "工具",
    "webstorm",
        "效率",
        "下载",
        "IDE"
    ],
    "thumbUp":0,
    "createTime":"2022-05-09 10:48",
    "viewCount":14,
    "cover":"https://images.sunofbeaches.com/content/2022_05_09/973174546246926336.png",
    "nickname":"拉大锯",
    "avatar":"https://images.sunofbeaches.com/content/2022_01_04/927902852251123712.png",
    "userId":null,
    "categoryName":null,
    "state":null,
    "description":null,
    "vip":false
}
 */
data class ShareBean(
    val avatar: String,
    val cover: String,
    val createTime: String,
    val id: String,
    val labels: List<String>,
    val nickname: String,
    val thumbUp: Int,
    val title: String,
    val url: String,
    val state: String?,
    val description: String?,
    val categoryName: String?,
    val userId: String?,
    val viewCount: Int,
    val vip: Boolean
): MultiItemEntity {
    override fun getItemType(): Int {
        return 2
    }
    fun labels(isSub: Boolean = true): String{
        return if(isSub){
            if(this.labels.size>3){
                this.labels.subList(0,3).toString().replace("[","").replace("]","")+" ..."
            }else{
                this.labels.toString().replace("[","").replace("]","")
            }
        }else{
            this.labels.toString().replace("[","").replace("]","")
        }
    }
}