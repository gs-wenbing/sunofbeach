package com.zwb.sob_moyu

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_base.bean.ListData
import com.zwb.lib_common.bean.TokenBean
import com.zwb.lib_common.bean.MoyuItemBean
import com.zwb.sob_moyu.bean.MomentCommentBean
import com.zwb.sob_moyu.bean.TopicIndexBean

class MoyuViewModel : BaseViewModel() {

    private val repository by lazy {
        MoyuRepo(loadState)
    }

    fun checkToken(key: String): MutableLiveData<TokenBean?> {
        val response: MutableLiveData<TokenBean?> = MutableLiveData()
        initiateRequest({
            response.value = repository.checkToken(key)
        }, loadState, key)
        return response
    }


    fun topicIndex(key: String): MutableLiveData<List<TopicIndexBean>?> {
        val response: MutableLiveData<List<TopicIndexBean>?> = MutableLiveData()
        initiateRequest({
            val list = repository.topicIndex(key)
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
                    response.value = repository.getRecommendList(page, key)
                }
                "2" -> {
                    response.value = repository.getFollowList(page, key)
                }
                else -> {
                    response.value = repository.getList(topicId, page, key)
                }
            }

        }, loadState, key)
        return response
    }

    fun getFollowList(
        momentId: String,
        page: Int,
        key: String
    ): MutableLiveData<ListData<MomentCommentBean>?> {
        val response: MutableLiveData<ListData<MomentCommentBean>?> = MutableLiveData()
        initiateRequest({
            response.value = repository.getFollowList(momentId, page, key)
        }, loadState, key)
        return response
    }

}