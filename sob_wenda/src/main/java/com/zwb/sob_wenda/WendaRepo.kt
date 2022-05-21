package com.zwb.sob_wenda

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.bean.ListData
import com.zwb.lib_base.ktx.dataConvert
import com.zwb.lib_base.mvvm.m.BaseRepository
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.net.State
import com.zwb.lib_common.CommonRepo
import com.zwb.sob_wenda.bean.AnswerBean
import com.zwb.sob_wenda.bean.WendaBean
import com.zwb.sob_wenda.bean.WendaContentBean
import com.zwb.sob_wenda.bean.WendaRankingBean

class WendaRepo(private val loadState: MutableLiveData<State>) : CommonRepo(loadState) {

    private val apiService by lazy {
        RetrofitFactory.instance.getService(WendaApi::class.java, WendaApi.BASE_URL)
    }

    suspend fun getWendaList(page: Int, state: String, key: String): ListData<WendaBean>? {
        return apiService.getWendaList(page, state).dataConvert(loadState, key)
    }

    suspend fun getWendaRankingList(key: String): List<WendaRankingBean>? {
        return apiService.getWendaRankingList().dataConvert(loadState, key)
    }

    suspend fun getWendaDetail(wendaId: String, key: String): WendaContentBean? {
        return apiService.getWendaDetail(wendaId).dataConvert(loadState, key)
    }

    suspend fun getWendaAnswerList(wendaId: String, page: Int, key: String): List<AnswerBean>? {
        return apiService.getWendaAnswerList(wendaId, page).dataConvert(loadState, key)
    }

    suspend fun getWendaRelative(wendaId: String, key: String): List<WendaBean>? {
        return apiService.getWendaRelative(wendaId).dataConvert(loadState, key)
    }

    suspend fun wendaThumbCheck(wendaId: String): BaseResponse<Int?> {
        return apiService.wendaThumbCheck(wendaId)
    }

    suspend fun wendaThumb(wendaId: String): BaseResponse<Int?> {
        return apiService.wendaThumb(wendaId)
    }
    suspend fun commentThumbCheck(wendaId: String): BaseResponse<Int?> {
        return apiService.commentThumbCheck(wendaId)
    }

    suspend fun commentThumb(wendaId: String): BaseResponse<Int?> {
        return apiService.commentThumb(wendaId)
    }

    suspend fun commentBest(wendaId: String, wendaCommentId: String): BaseResponse<Int?> {
        return apiService.commentBest(wendaId,wendaCommentId)
    }

    suspend fun commentPrise(commentId: String, count: Int, thumbUp: Boolean): BaseResponse<Int?> {
        return apiService.commentPrise(commentId, count, thumbUp)
    }


}