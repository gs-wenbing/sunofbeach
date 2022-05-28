package com.zwb.sob_home

import com.zwb.lib_base.bean.ListData
import com.zwb.lib_base.bean.PageViewData
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_common.bean.CollectInputBean
import com.zwb.lib_common.bean.TokenBean
import com.zwb.lib_common.constant.Constants
import com.zwb.sob_home.bean.*
import retrofit2.http.*

interface HomeApi {
    @GET(Constants.URL.CHECK_TOKEN_URL)
    suspend fun checkToken(): BaseResponse<TokenBean?>

    @GET(CATEGORY_LIST_URL)
    suspend fun categoryList(): BaseResponse<List<CategoryBean>?>

    @GET(BANNER_URL)
    suspend fun getBanner(): BaseResponse<List<BannerBean>?>

    /**
     * 获取推荐内容 & 根据分类获取内容
     */
    @GET("${RECOMMEND_URL}/{categoryId}/{page}")
    suspend fun getRecommendByCategoryId(
        @Path("categoryId") categoryId: String,
        @Path("page") page: Int
    ): BaseResponse<ListData<HomeItemBean>?>

    /**
     * 获取推荐内容
     */
    @GET("${RECOMMEND_URL}/{page}")
    suspend fun getRecommend(
        @Path("page") page: Int
    ): BaseResponse<ListData<HomeItemBean>?>

    /**
     * 文章详情
     */
    @GET("${ARTICLE_DETAIL_URL}/{articleId}")
    suspend fun getArticleDetail(
        @Path("articleId") articleId: String
    ): BaseResponse<ArticleDetailBean?>

    /**
     * 文章点赞
     */
    @PUT("${ARTICLE_THUMBUP_URL}/{articleId}")
    suspend fun articleThumbUp(
        @Path("articleId") articleId: String
    ): BaseResponse<Int?>

    /**
     * 文章点赞数
     */
    @GET("${ARTICLE_CHECK_THUMBUP_URL}/{articleId}")
    suspend fun checkArticleThumbUp(
        @Path("articleId") articleId: String
    ): BaseResponse<Int?>

//    /**
//     * 获取收藏文件夹
//     */
//    @GET("${COLLECTION_LIST_URL}/{page}")
//    suspend fun collectionList(
//        @Path("page") page: Int
//    ): BaseResponse<PageViewData<CollectionBean>?>


    /**
     * 获取相关文章列表
     * size:需要多少个相关文章
     */
    @GET("${ARTICLE_RECOMMEND_URL}/{articleId}/{size}")
    suspend fun getArticleRecommend(
        @Path("articleId") articleId: String,
        @Path("size") size: Int
    ): BaseResponse<List<ArticleRecommendBean>?>


    /**
     * 回复评论 POST
     */
    @POST(ARTICLE_SUB_COMMENT_URL)
    suspend fun replyComment(
        @Body comment: SubCommentInputBean
    ): BaseResponse<String?>
    /**
     * 评论文章 POST
     */
    @POST(ARTICLE_COMMENT_URL)
    suspend fun commentArticle(
        @Body comment: CommentInputBean
    ): BaseResponse<String?>

    /**
     * 评论列表
     */
    @GET("${ARTICLE_COMMENT_URL}/{articleId}/{page}")
    suspend fun getArticleCommentList(
        @Path("articleId") articleId: String,
        @Path("page") page: Int
    ): BaseResponse<PageViewData<CommentBean>?>

    /**
     * 打赏列表
     */
    @GET("${PRISE_ARTICLE_URL}/{articleId}")
    suspend fun getPriseArticleList(
        @Path("articleId") articleId: String,
    ): BaseResponse<List<PriseArticleBean>?>

    /**
     * 打赏文章
     */
    @POST(PRISE_ARTICLE_URL)
    suspend fun priseArticle(
        @Body priseArticle: PriseArticleInputBean,
    ): BaseResponse<String?>


    companion object {
        const val BASE_URL = "https://api.sunofbeaches.com/"

        // 获取推荐内容 & 根据分类获取内容
        const val RECOMMEND_URL = "ct/content/home/recommend"

        // 获取分类列表
        const val CATEGORY_LIST_URL = "ct/category/list"

        // 获取轮播图
        const val BANNER_URL = "ast/home/loop/list"

        // 文章详情 /ct/article/detail/{articleId}
        const val ARTICLE_DETAIL_URL = "ct/article/detail"

        // 文章点赞 put ct/article/thumb-up/{articleId}
        const val ARTICLE_THUMBUP_URL = "ct/article/thumb-up"

        // 文章点赞数 get ct/article/check-thumb-up/{articleId}
        const val ARTICLE_CHECK_THUMBUP_URL = "ct/article/check-thumb-up"

//        // 收藏夹 get ct/collection/list/{page}
//        const val COLLECTION_LIST_URL = "ct/collection/list"

        // 收藏(POST) & 取消收藏(DELETE)/{favoriteId}
        const val FAVORITE_URL = "ct/favorite"

        // 是否收藏（GET: url=文章地址） 返回值：{"success":true,"code":10000,"message":"操作成功","data":"972137212613230592"}  data="0" 未收藏
        const val CHECK_COLLECTED_URL = "ct/favorite/checkCollected"

        // 评论文章 POST
        // 获取文章评论列表 /ct/article/comment/{articleId}/{page}
        const val ARTICLE_COMMENT_URL = "ct/article/comment"

        // 获取相关文章列表 /ct/article/recommend/{articleId}/{size}
        const val ARTICLE_RECOMMEND_URL = "ct/article/recommend"

        //  打赏文章 post /ast/prise/article
        //  获取打赏列表 ast/prise/article/{articleId}
        const val PRISE_ARTICLE_URL = "ast/prise/article"

        //  回复文章评论
        const val ARTICLE_SUB_COMMENT_URL = "ct/article/sub-comment"

    }
}