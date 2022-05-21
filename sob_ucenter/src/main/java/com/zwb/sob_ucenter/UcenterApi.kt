package com.zwb.sob_ucenter

import com.zwb.lib_base.bean.ListData
import com.zwb.lib_base.bean.PageViewData
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_common.bean.MoyuItemBean
import com.zwb.lib_common.bean.TokenBean
import com.zwb.lib_common.bean.UserBean
import com.zwb.lib_common.constant.Constants
import com.zwb.sob_ucenter.bean.*
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path

interface UcenterApi {

    @GET(Constants.URL.CHECK_TOKEN_URL)
    suspend fun checkToken(): BaseResponse<TokenBean?>

    @GET("${USER_INFO_URL}/{userId}")
    suspend fun getUserInfo(@Path("userId") userId: String): BaseResponse<UserBean?>

    @GET(TOTAL_SOB_URL)
    suspend fun getTotalSobCoin(): BaseResponse<Int?>

    @GET("${SOB_LIST_URL}/{userId}/{page}")
    suspend fun getSobList(
        @Path("userId") userId: String,
        @Path("page") page: Int
    ): BaseResponse<ListData<SobBean>?>


    @GET(LOGOUT_URL)
    suspend fun logout(): BaseResponse<String?>

    @GET("${AVATAR_URL}/{phoneNum}")
    suspend fun avatar(@Path("phoneNum") phoneNum: String): BaseResponse<String?>

    /**
     * 获取用户文章列表
     */
    @GET("${USER_ARTICLE_LIST_URL}/{userId}/{page}")
    suspend fun getUserArticleList(
        @Path("userId") userId: String,
        @Path("page") page: Int
    ): BaseResponse<ListData<ArticleBean>?>

    /**
     * 获取用户动态列表
     */
    @GET("${USER_MOYU_LIST_URL}/{userId}/{page}")
    suspend fun getUserMoyuList(
        @Path("userId") userId: String,
        @Path("page") page: Int
    ): BaseResponse<List<MoyuItemBean>?>

    /**
     * 获取用户分享列表
     */
    @GET("${USER_SHARE_LIST_URL}/{userId}/{page}")
    suspend fun getUserShareList(
        @Path("userId") userId: String,
        @Path("page") page: Int
    ): BaseResponse<ListData<ShareBean>?>

    /**
     * 获取用户问答列表
     */
    @GET("${USER_WENDA_LIST_URL}/{userId}/{page}")
    suspend fun getUserWendaList(
        @Path("userId") userId: String,
        @Path("page") page: Int
    ): BaseResponse<PageViewData<UserWendaBean>?>

    /**
     * 个人成绩
     */
    @GET("${USER_ACHIEVEMENT_URL}/{userId}")
    suspend fun getUserAchievement(
        @Path("userId") userId: String,
    ): BaseResponse<AchievementBean?>

    /**
     * 个人成绩
     */
    @GET(USER_ACHIEVEMENT_URL)
    suspend fun getMyAchievement(): BaseResponse<AchievementBean?>

    /**
     * 获取消息数
     */
    @GET(USER_MSG_COUNT_URL)
    suspend fun getMsgCount(): BaseResponse<MsgCountBean?>

    /**
     * 获取关注列表
     */
    @GET("${FOLLOW_LIST_URL}/{userId}/{page}")
    suspend fun followList(
        @Path("userId") userId: String,
        @Path("page") page: Int
    ): BaseResponse<ListData<FollowBean>?>

    /**
     * 获取关注列表
     */
    @GET("${FANS_LIST_URL}/{userId}/{page}")
    suspend fun fansList(
        @Path("userId") userId: String,
        @Path("page") page: Int
    ): BaseResponse<ListData<FollowBean>?>

    /**
     * 全部已读
     */
    @GET(MSG_READ_URL)
    suspend fun msgRead(): BaseResponse<String?>

    /**
     * 获取sob排行榜
     */
    @GET("${RANKING_SOB_URL}/{count}")
    suspend fun rankingSob(
        @Path("count") count: Int = 30
    ): BaseResponse<List<RankingSobBean>?>

    /**
     * 获取到收藏夹里的内容列表
     */
    @GET("${FAVORITE_LIST_URL}/{collectionId}/{page}/{order}")
    suspend fun favoriteList(
        @Path("collectionId") collectionId: String,
        @Path("page") page: Int,
        @Path("order") order: Int = 0,
    ): BaseResponse<PageViewData<FavoriteBean>?>

    /**
     * 获取消息中评论列表
     */
    @GET("${MESSAGE_MOMENT_URL}/{page}")
    suspend fun messageMomentList(
        @Path("page") page: Int,
    ): BaseResponse<PageViewData<MsgMomentBean>?>

    /**
     * 获取消息中点赞列表
     */
    @GET("${MESSAGE_THUMB_URL}/{page}")
    suspend fun messageThumbList(
        @Path("page") page: Int,
    ): BaseResponse<PageViewData<MsgThumbBean>?>

    /**
     * 获取消息中AT列表
     */
    @GET("${MESSAGE_AT_URL}/{page}")
    suspend fun messageAtList(
        @Path("page") page: Int,
    ): BaseResponse<PageViewData<MsgAtBean>?>

    /**
     * 获取系统消息列表
     */
    @GET("${MESSAGE_SYSTEM_URL}/{page}")
    suspend fun messageSystemList(
        @Path("page") page: Int,
    ): BaseResponse<PageViewData<MsgSystemBean>?>

    /**
     * 获取文章消息列表
     */
    @GET("${MESSAGE_ARTICLE_URL}/{page}")
    suspend fun messageArticleList(
        @Path("page") page: Int,
    ): BaseResponse<PageViewData<MsgArticleBean>?>

    /**
     * 更新摸鱼动态消息的状态
     */
    @PUT("${MESSAGE_ARTICLE_STATE_URL}/{msgId}/1")
    suspend fun articleState(
        @Path("msgId") msgId: String,
    ): BaseResponse<String?>

    /**
     * 更新摸鱼动态消息的状态
     */
    @PUT("${MESSAGE_MOMENT_STATE_URL}/{msgId}/1")
    suspend fun momentState(
        @Path("msgId") msgId: String,
    ): BaseResponse<String?>

    /**
     * 更新at动态消息的状态
     */
    @PUT("${MESSAGE_AT_STATE_URL}/{msgId}/1")
    suspend fun atState(
        @Path("msgId") msgId: String,
    ): BaseResponse<String?>

    /**
     * 更新问题回答消息的状态
     */
    @PUT("${MESSAGE_WENDA_STATE_URL}/{msgId}/1")
    suspend fun wendaState(
        @Path("msgId") msgId: String,
    ): BaseResponse<String?>

    companion object {
        const val BASE_URL = "https://api.sunofbeaches.com/"

        // 获取用户信息
        const val USER_INFO_URL = "uc/user-info"

        // 获取sob币
        const val TOTAL_SOB_URL = "ast/ucenter/total-sob"

        // 获取sob币列表 {userId}/{page}
        const val SOB_LIST_URL = "ast/ucenter/sob-trade"

        // 退出登录
        const val LOGOUT_URL = "uc/user/logout"

        // 通过手机号码获取头像(好像用不了)
        const val AVATAR_URL = "https://api.sunofbeach.net/uc/user/avatar"

        // 获取用户文章列表
        const val USER_ARTICLE_LIST_URL = "ct/article/list"

        // 获取动态文章列表
        const val USER_MOYU_LIST_URL = "ct/moyu/list/user"

        // 获取用户问答列表 {1204736502274318336/1}
        const val USER_WENDA_LIST_URL = "ct/wenda/comment/list/user"

        // 获取用户分享列表 {1204736502274318336/1}
        const val USER_SHARE_LIST_URL = "ct/share/list"

        // 获取未读信息/ct/msg/count
        const val USER_MSG_COUNT_URL = "ct/msg/count"

        // 获取个人成就 /ast/achievement/{1204736502274318336}
        const val USER_ACHIEVEMENT_URL = "ast/achievement"

        // 关注列表 get /uc/follow/list/{userId}/{page}
        const val FOLLOW_LIST_URL = "uc/follow/list"

        // 粉丝列表 get /uc/follow/list/{userId}/{page}
        const val FANS_LIST_URL = "uc/fans/list"

        // 一键已读 请求方法:暂时是GET
        const val MSG_READ_URL = "ct/msg/read"

        // 获取富豪排行榜 ast/rank/sob/{count} count，获取数量
        const val RANKING_SOB_URL = "ast/rank/sob"

        // 获取到收藏夹里的内容列表 {collectionId}/{page}/{order}  order：排序方式 0 表示降序，1表示升序，按添加时间
        const val FAVORITE_LIST_URL = "ct/ucenter/favorite/list"

        // 获取消息中心回答列表列表 ct/ucenter/message/wenda/{page}
        const val MESSAGE_WENDA_URL = "ct/ucenter/message/wenda"

        // 获取消息中心点赞列表 ct/ucenter/message/thumb/{page}
        const val MESSAGE_THUMB_URL = "ct/ucenter/message/thumb"

        // 获取消息中心动态评论列表 ct/ucenter/message/moment/{page}
        const val MESSAGE_MOMENT_URL = "ct/ucenter/message/moment"

        // 获取消息中心at列表 ct/ucenter/message/at/{page}
        const val MESSAGE_AT_URL = "ct/ucenter/message/at"

        // 获取系统消息列表 ct/ucenter/message/system/{page}
        const val MESSAGE_SYSTEM_URL = "ct/ucenter/message/system"

        // 获取文章评论列表 ct/ucenter/message/article/{page}
        const val MESSAGE_ARTICLE_URL = "ct/ucenter/message/article"

        // 更新文章消息状态 /ct/ucenter/message/state/{msgId}/1  1表示已读，2表示已回复（可以借助前面的文章子评论回复功能）
        const val MESSAGE_ARTICLE_STATE_URL = "ct/ucenter/message/state"

        // 更新摸鱼动态消息的状态 ct/ucenter/message/moment/state/{msgId}/1
        const val MESSAGE_MOMENT_STATE_URL = "ct/ucenter/message/moment/state"

        // 更新At消息的状态 ct/ucenter/message/at/state/{msgId}/1
        const val MESSAGE_AT_STATE_URL = "ct/ucenter/message/at/state"

        // 更新问题回答消息的状态 ct/ucenter/message/wenda/state/{msgId}/1
        const val MESSAGE_WENDA_STATE_URL = "ct/ucenter/message/wenda/state"
    }
}