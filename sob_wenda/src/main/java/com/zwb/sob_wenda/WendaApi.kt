package com.zwb.sob_wenda

import com.zwb.lib_base.bean.ListData
import com.zwb.lib_base.net.BaseResponse
import com.zwb.sob_wenda.bean.AnswerBean
import com.zwb.sob_wenda.bean.WendaBean
import com.zwb.sob_wenda.bean.WendaContentBean
import com.zwb.sob_wenda.bean.WendaRankingBean
import retrofit2.http.GET
import retrofit2.http.PUT
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

    /**
     * 查询当前用户是否有点赞该问题
     */
    @GET("${WENDA_THUMB_CHECK_URL}/{wendaId}")
    suspend fun wendaThumbCheck(
        @Path("wendaId") wendaId: String
    ): BaseResponse<Int?>

    /**
     * 检查是否有点赞某个回答
     */
    @GET("${WENDA_COMMENT_THUMB_CHECK_URL}/{commentId}")
    suspend fun commentThumbCheck(
        @Path("commentId") commentId: String
    ): BaseResponse<Int?>

    /**
     * 问题点赞
     */
    @PUT("${WENDA_THUMB_URL}/{wendaId}")
    suspend fun wendaThumb(
        @Path("wendaId") wendaId: String
    ): BaseResponse<Int?>

    /**
     * 问题回答点赞
     */
    @PUT("${WENDA_COMMENT_THUMB_URL}/{wendaCommentId}")
    suspend fun commentThumb(
        @Path("wendaCommentId") wendaCommentId: String
    ): BaseResponse<Int?>

    /**
     * 设置为最佳答案
     */
    @PUT("${WENDA_COMMENT_BEST_URL}/{wendaId}/{wendaCommentId}")
    suspend fun commentBest(
        @Path("wendaId") wendaId: String,
        @Path("wendaCommentId") wendaCommentId: String
    ): BaseResponse<Int?>

    /**
     * 答案打赏
     */
    @PUT("${WENDA_COMMENT_PRISE_URL}/{commentId}/{count}")
    suspend fun commentPrise(
        @Path("commentId") commentId: String,
        @Path("count") count: Int,
        @Query("thumbUp") thumbUp: Boolean,
    ): BaseResponse<Int?>

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

        //查询当前用户是否有点赞该问题 GET /ct/wenda/thumb-up/check/{wendaId}
        const val WENDA_THUMB_CHECK_URL = "ct/wenda/thumb-up/check"

        //问题点赞  PUT ct/wenda/thumb-up/{wendaId}
        const val WENDA_THUMB_URL = "ct/wenda/thumb-up"

        //检查是否有点赞某个回答 GET /ct/wenda/comment/thumb-up/check/{commentId}
        const val WENDA_COMMENT_THUMB_CHECK_URL = "ct/wenda/comment/thumb-up/check"

        //问题回答点赞 PUT /ct/wenda/comment/thumb-up/{wendaCommentId}
        const val WENDA_COMMENT_THUMB_URL = "ct/wenda/comment/thumb-up"

        //设置为最佳答案 PUT /ct/wenda/comment/best/{wendaId}/{wendaCommentId}
        //此接口只在当前用户与当前问答的用户一样时才可以显示，才可以调用，否则返回无权限的结果
        const val WENDA_COMMENT_BEST_URL = "ct/wenda/comment/best"

        //答案打赏 PUT /ct/wenda/comment/prise/{commentId}/{count}?thumbUp=thumb
        //commentId 答案id; count 打赏积分数量; thumb：true/false true表示同时点赞
        const val WENDA_COMMENT_PRISE_URL = "ct/wenda/comment/prise"
    }
}