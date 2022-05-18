package com.zwb.sob_wenda

import com.zwb.lib_base.bean.ListData
import com.zwb.lib_base.net.BaseResponse
import com.zwb.sob_wenda.bean.AnswerBean
import com.zwb.sob_wenda.bean.WendaBean
import com.zwb.sob_wenda.bean.WendaContentBean
import com.zwb.sob_wenda.bean.WendaRankingBean
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface WendaApi {

    @GET(WENDA_LIST_URL)
    suspend fun getWendaList(
        @Query("page") page: Int,
        @Query("state") state: String,
        @Query("category") category: Int = -2
    ): BaseResponse<ListData<WendaBean>?>


    @GET("$WENDA_RANKING_LIST_URL/{size}")
    suspend fun getWendaRankingList(
        @Path("size") size: Int = 10,
    ): BaseResponse<List<WendaRankingBean>?>


    @GET("$WENDA_DETAIL_URL/{wendaId}")
    suspend fun getWendaDetail(
        @Path("wendaId") wendaId: String,
    ): BaseResponse<WendaContentBean?>


    @GET("$WENDA_ANSWER_LIST_URL/{wendaId}/{page}")
    suspend fun getWendaAnswerList(
        @Path("wendaId") wendaId: String,
        @Path("page") page: Int
    ): BaseResponse<List<AnswerBean>?>


    @GET("$WENDA_RELATIVE_URL/{wendaId}/{size}")
    suspend fun getWendaRelative(
        @Path("wendaId") wendaId: String,
        @Path("size") page: Int=5
    ): BaseResponse<List<WendaBean>?>

    companion object {
        const val BASE_URL = "https://api.sunofbeaches.com/"

        // 问答列表 lastest:最新 hot:热门
        const val WENDA_LIST_URL = "ct/wenda/list"

        // 排行榜 size 数量5~10
        const val WENDA_RANKING_LIST_URL = "ast/rank/answer-count"

        // 获取问答详情 {wendaId} 问题的Id
        const val WENDA_DETAIL_URL = "ct/wenda"

        // 获取答案列表 {wendaId}/{page};
        const val WENDA_ANSWER_LIST_URL = "ct/wenda/comment/list"

        // 获取相关的问题 {wendaId}/{size};
        const val WENDA_RELATIVE_URL = "ct/wenda/relative"

    }
}