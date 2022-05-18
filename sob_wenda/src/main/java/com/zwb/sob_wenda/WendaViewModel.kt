package com.zwb.sob_wenda

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.bean.ListData
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_common.CommonViewModel
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.sob_wenda.bean.AnswerBean
import com.zwb.sob_wenda.bean.WendaBean
import com.zwb.sob_wenda.bean.WendaContentBean
import com.zwb.sob_wenda.bean.WendaRankingBean

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


}