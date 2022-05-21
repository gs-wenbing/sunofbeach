package com.zwb.sob_moyu

import com.zwb.lib_base.bean.ListData
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_common.CommonApi
import com.zwb.lib_common.bean.MoyuItemBean
import com.zwb.lib_common.bean.TokenBean
import com.zwb.lib_common.constant.Constants
import com.zwb.sob_moyu.bean.MomentCommentBean
import com.zwb.sob_moyu.bean.MomentCommentInputBean
import com.zwb.sob_moyu.bean.TopicIndexBean
import retrofit2.http.*

interface MoyuApi {

    @GET(Constants.URL.CHECK_TOKEN_URL)
    suspend fun checkToken(): BaseResponse<TokenBean?>

    @GET(TOPIC_INDEX_URL)
    suspend fun topicIndex(): BaseResponse<List<TopicIndexBean>?>

    @GET("${LIST_URL}/{topicId}/{page}")
    suspend fun getList(
        @Path("topicId") topicId: String,
        @Path("page") page: Int
    ): BaseResponse<ListData<MoyuItemBean>?>


    @GET("${FOLLOW_LIST_URL}/{page}")
    suspend fun getFollowList(
        @Path("page") page: Int
    ): BaseResponse<ListData<MoyuItemBean>?>


    @GET("${RECOMMEND_LIST_URL}/{page}")
    suspend fun getRecommendList(
        @Path("page") page: Int
    ): BaseResponse<ListData<MoyuItemBean>?>


    @GET("${COMMENT_URL}/{momentId}/{page}")
    suspend fun getCommentList(
        @Path("momentId") momentId: String,
        @Path("page") size: Int,
        @Query("sort") sort: Int = 1
    ): BaseResponse<ListData<MomentCommentBean>?>


    @PUT("${THUMB_UP_URL}/{momentId}")
    suspend fun thumbUp(
        @Path("momentId") momentId: String
    ): BaseResponse<String?>

    @GET("${MOYU_DETAIL_URL}/{momentId}")
    suspend fun moyuDetail(
        @Path("momentId") momentId: String
    ): BaseResponse<MoyuItemBean?>


    /**
     * 动态点赞
     */
    @PUT("${MOYU_THUMB_URL}/{momentId}")
    suspend fun moyuThumb(
        @Path("momentId") momentId: String
    ): BaseResponse<Int?>

    @POST(COMMENT_URL)
    suspend fun comment(@Body query: MomentCommentInputBean): BaseResponse<String?>

    companion object {
        const val BASE_URL = "https://api.sunofbeaches.com/"

        // 关注列表
        const val FOLLOW_LIST_URL = "ct/moyu/list/follow"
        // 推荐列表
        const val RECOMMEND_LIST_URL = "ct/moyu/list/recommend"

        // 获取动态列表
        const val LIST_URL = "ct/moyu/list"

        // 获取热门动态列表
        const val HOT_URL = "ct/moyu/hot"


        // 获取动态评论/发表评论(评论动态)
        const val COMMENT_URL = "ct/moyu/comment"

        // 动态点赞
        const val THUMB_UP_URL = "ct/moyu/thumb-up"

        // 获取首页话题（类似于摸鱼首页侧栏）
        const val TOPIC_INDEX_URL = "ct/moyu/topic/index"

        // 获取话题详情 {momentId}
        const val MOYU_DETAIL_URL = "ct/moyu"

        // 动态点赞 PUT ct/moyu/thumb-up/{momentId}
        const val MOYU_THUMB_URL = "ct/moyu/thumb-up"
    }
}