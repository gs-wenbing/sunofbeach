package com.zwb.sob_ucenter.bean

import com.chad.library.adapter.base.entity.MultiItemEntity


data class ArticleBean(
    val articleType: Int?,
    val avatar: String?,
    val covers: List<String> = mutableListOf(),
    val createTime: String?,
    val id: String?,
    val labels: List<String> = mutableListOf(),
    val nickname: String?,
    val state: Int?,
    val thumbUp: Int?,
    val title: String?,
    val userId: String?,
    val viewCount: Int?,
    val vip: Boolean
): MultiItemEntity {
    override fun getItemType(): Int {
        return 1
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