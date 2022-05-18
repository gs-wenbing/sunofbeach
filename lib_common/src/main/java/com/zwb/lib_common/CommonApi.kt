package com.zwb.lib_common

import com.zwb.lib_base.bean.PageViewData
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_common.bean.CollectInputBean
import com.zwb.lib_common.bean.CollectionBean
import com.zwb.lib_common.bean.PriseQrCode
import com.zwb.lib_common.bean.TokenBean
import com.zwb.lib_common.constant.Constants
import retrofit2.http.*

interface CommonApi {
    @GET(Constants.URL.CHECK_TOKEN_URL)
    suspend fun checkToken(): BaseResponse<TokenBean?>

    /**
     * 获取作者的打赏二维码
     */
    @GET("$PRISE_QR_CODE_URL/{userId}")
    suspend fun getPriseQrCode(
        @Path("userId") userId: String
    ): BaseResponse<PriseQrCode?>


    /**
     * 获取收藏文件夹
     */
    @GET("${COLLECTION_LIST_URL}/{page}")
    suspend fun collectionList(
        @Path("page") page: Int
    ): BaseResponse<PageViewData<CollectionBean>?>


    /**
     * 检测是否已经收藏
     */
    @GET(CHECK_COLLECTED_URL)
    suspend fun checkCollected(
        @Query("url") url: String
    ): BaseResponse<String?>

    /**
     * 收藏
     */
    @POST(FAVORITE_URL)
    suspend fun favorite(@Body body: CollectInputBean): BaseResponse<String?>

    /**
     * 取消收藏
     */
    @DELETE("${FAVORITE_URL}/{favoriteId}")
    suspend fun unFavorite(
        @Path("favoriteId") favoriteId: String
    ): BaseResponse<String?>

    /**
     * 关注
     */
    @POST("${FOLLOW_URL}/{userId}")
    suspend fun follow(
        @Path("userId") userId: String
    ): BaseResponse<Int?>

    /**
     * 取消关注
     */
    @DELETE("${UN_FOLLOW_URL}/{userId}")
    suspend fun unFollow(
        @Path("userId") userId: String
    ): BaseResponse<Int?>

    /**
     * 查询自己与目标用户的关注状态
     */
    @GET("${FOLLOW_STATE_URL}/{userId}")
    suspend fun followState(
        @Path("userId") userId: String
    ): BaseResponse<Int?>

    companion object {
        const val BASE_URL = "https://api.sunofbeaches.com/"

        // 获取作者的打赏二维码 ast/prise-qr-code/{userId}
        const val PRISE_QR_CODE_URL = "ast/prise-qr-code"


        // 收藏(POST) & 取消收藏(DELETE)/{favoriteId}
        const val FAVORITE_URL = "ct/favorite"

        // 是否收藏（GET: url=文章地址） 返回值：{"success":true,"code":10000,"message":"操作成功","data":"972137212613230592"}  data="0" 未收藏
        const val CHECK_COLLECTED_URL = "ct/favorite/checkCollected"


        // 收藏夹 get ct/collection/list/{page}
        const val COLLECTION_LIST_URL = "ct/collection/list"

        // 添加关注 Post /uc/fans/{userId}
        const val FOLLOW_URL = "uc/fans"

        // 取消关注 Delete  /uc/fans/{userId}
        const val UN_FOLLOW_URL = "uc/fans"

        // 查询自己与目标用户的关注状态 GET /uc/fans/state/{userId}
        //        0表示没有关注对方，可以显示为：关注
        //        1表示对方关注自己，可以显示为：回粉
        //        2表示已经关注对方，可以显示为：已关注
        //        3表示相互关注，可以显示为：相互关注
        const val FOLLOW_STATE_URL = "uc/fans/state"
    }
}