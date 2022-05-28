package com.zwb.sob_wenda

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.bean.ListData
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_common.CommonViewModel
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.sob_wenda.bean.*

class WendaViewModel: CommonViewModel() {

    private val wendaRepo by lazy {
        WendaRepo(loadState)
    }

    fun getWendaList(page: Int, state: String, key: String): MutableLiveData<ListData<WendaBean>?> {
        val response: MutableLiveData<ListData<WendaBean>?> = MutableLiveData()
        initiateRequest({
            response.value = wendaRepo.getWendaList(page, state, key)
        }, loadState, key)
        return response
    }

    fun getWendaRankingList(key: String): MutableLiveData<List<WendaRankingBean>?> {
        val response: MutableLiveData<List<WendaRankingBean>?> = MutableLiveData()
        initiateRequest({
            response.value = wendaRepo.getWendaRankingList(key)
        }, loadState, key)
        return response
    }

    fun getWendaDetail(wendaId: String, key: String): MutableLiveData<WendaContentBean?> {
        val response: MutableLiveData<WendaContentBean?> = MutableLiveData()
        initiateRequest({
            response.value = wendaRepo.getWendaDetail(wendaId, key)
        }, loadState, key)
        return response
    }

    fun getWendaAnswerList(wendaId: String, page: Int, key: String): MutableLiveData<List<AnswerBean>?> {
        val response: MutableLiveData<List<AnswerBean>?> = MutableLiveData()
        initiateRequest({
            response.value = wendaRepo.getWendaAnswerList(wendaId, page, key)
        }, loadState, key)
        return response
    }

    fun getWendaRelative(wendaId: String, key: String): MutableLiveData<List<WendaBean>?> {
        val response: MutableLiveData<List<WendaBean>?> = MutableLiveData()
        initiateRequest({
            response.value = wendaRepo.getWendaRelative(wendaId, key)
        }, loadState, key)
        return response
    }

    fun wendaThumbCheck(wendaId: String): MutableLiveData<BaseResponse<Int?>> {
        val response: MutableLiveData<BaseResponse<Int?>> = MutableLiveData()
        initiateRequest({
            response.value = wendaRepo.wendaThumbCheck(wendaId)
        }, loadState)
        return response
    }

    fun wendaThumb(wendaId: String): MutableLiveData<BaseResponse<Int?>> {
        val response: MutableLiveData<BaseResponse<Int?>> = MutableLiveData()
        initiateRequest({
            response.value = wendaRepo.wendaThumb(wendaId)
        }, loadState)
        return response
    }

    fun commentThumbCheck(wendaId: String): MutableLiveData<BaseResponse<Int?>> {
        val response: MutableLiveData<BaseResponse<Int?>> = MutableLiveData()
        initiateRequest({
            response.value = wendaRepo.commentThumbCheck(wendaId)
        }, loadState)
        return response
    }

    fun commentThumb(wendaId: String): MutableLiveData<BaseResponse<Int?>> {
        val response: MutableLiveData<BaseResponse<Int?>> = MutableLiveData()
        initiateRequest({
            response.value = wendaRepo.commentThumb(wendaId)
        }, loadState)
        return response
    }

    fun commentBest(wendaId: String, wendaCommentId: String): MutableLiveData<BaseResponse<Int?>> {
        val response: MutableLiveData<BaseResponse<Int?>> = MutableLiveData()
        initiateRequest({
            response.value = wendaRepo.commentBest(wendaId, wendaCommentId)
        }, loadState)
        return response
    }

    fun commentPrise(commentId: String, count: Int, thumbUp: Boolean): MutableLiveData<BaseResponse<Int?>> {
        val response: MutableLiveData<BaseResponse<Int?>> = MutableLiveData()
        initiateRequest({
            response.value = wendaRepo.commentPrise(commentId, count, thumbUp)
        }, loadState)
        return response
    }
    fun answer(body: AnswerInputBean): MutableLiveData<BaseResponse<Int?>> {
        val response: MutableLiveData<BaseResponse<Int?>> = MutableLiveData()
        initiateRequest({
            response.value = wendaRepo.answer(body)
        }, loadState)
        return response
    }

    fun replyAnswer(body: WendaSubCommentInputBean): MutableLiveData<BaseResponse<Int?>> {
        val response: MutableLiveData<BaseResponse<Int?>> = MutableLiveData()
        initiateRequest({
            response.value = wendaRepo.replyAnswer(body)
        }, loadState)
        return response
    }

}