package com.zwb.sob_moyu

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.bean.ListData
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_common.CommonViewModel
import com.zwb.lib_common.bean.TokenBean
import com.zwb.lib_common.bean.MoyuItemBean
import com.zwb.sob_moyu.bean.MomentCommentBean
import com.zwb.sob_moyu.bean.TopicIndexBean

class MoyuViewModel : CommonViewModel() {

    private val moyuRepo by lazy {
        MoyuRepo(loadState)
    }


    fun topicIndex(key: String): MutableLiveData<List<TopicIndexBean>?> {
        val response: MutableLiveData<List<TopicIndexBean>?> = MutableLiveData()
        initiateRequest({
            val list = moyuRepo.topicIndex(key)
            response.value = list?.subList(0, 2)
        }, loadState, key)
        return response
    }

    /**
     * 摸鱼列表
     */
    fun getList(topicId: String, page: Int, key: String): MutableLiveData<ListData<MoyuItemBean>?> {
        val response: MutableLiveData<ListData<MoyuItemBean>?> = MutableLiveData()
        initiateRequest({
            when (topicId) {
                "1" -> {
                    response.value = moyuRepo.getRecommendList(page, key)
                }
                "2" -> {
                    response.value = moyuRepo.getFollowList(page, key)
                }
                else -> {
                    response.value = moyuRepo.getList(topicId, page, key)
                }
            }

        }, loadState, key)
        return response
    }

    fun getCommentList(
        momentId: String,
        page: Int,
        key: String
    ): MutableLiveData<ListData<MomentCommentBean>?> {
        val response: MutableLiveData<ListData<MomentCommentBean>?> = MutableLiveData()
        initiateRequest({
            response.value = moyuRepo.getCommentList(momentId, page, key)
        }, loadState, key)
        return response
    }

    fun moyuDetail(momentId: String, key: String): MutableLiveData<MoyuItemBean?> {
        val response: MutableLiveData<MoyuItemBean?> = MutableLiveData()
        initiateRequest({
            response.value = moyuRepo.moyuDetail(momentId, key)
        }, loadState, key)
        return response
    }
    /**
     * 动态点赞
     */
    fun moyuThumb(momentId: String): MutableLiveData<BaseResponse<Int?>> {
        val response: MutableLiveData<BaseResponse<Int?>> = MutableLiveData()
        initiateRequest({
            response.value = moyuRepo.moyuThumb(momentId)
        }, loadState)
        return response
    }
}