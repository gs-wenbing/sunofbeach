package com.zwb.sob_home

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.bean.ListData
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_common.CommonViewModel
import com.zwb.lib_common.bean.CollectInputBean
import com.zwb.sob_home.bean.*

class HomeViewModel : CommonViewModel() {

    private val homeRepo by lazy {
        HomeRepo(loadState)
    }

    fun categoryList(key: String): MutableLiveData<List<CategoryBean>?> {
        val response: MutableLiveData<List<CategoryBean>?> = MutableLiveData()
        initiateRequest({
            val list = homeRepo.categoryList(key)
            response.value = list?.subList(0, 3)
        }, loadState, key)
        return response
    }

    /**
     * 首页列表
     */
    fun getList(
        categoryId: String,
        page: Int,
        key: String
    ): MutableLiveData<ListData<HomeItemBean>?> {
        val response: MutableLiveData<ListData<HomeItemBean>?> = MutableLiveData()
        initiateRequest({
            when (categoryId) {
                "1" -> {
                    response.value = homeRepo.getRecommend(page, key)
                }
                else -> {
                    response.value = homeRepo.getRecommendByCategoryId(categoryId, page, key)
                }
            }
        }, loadState, key)
        return response
    }


    fun getBanner(key: String): MutableLiveData<BannerList> {
        val response: MutableLiveData<BannerList> = MutableLiveData()
        initiateRequest({
            val bannerList = homeRepo.getBanner(key)
            bannerList?.let {
                response.value = BannerList(it)
            }
        }, loadState, key)
        return response
    }

    fun getArticleDetail(articleId: String, key: String): MutableLiveData<ArticleDetailBean?> {
        val response: MutableLiveData<ArticleDetailBean?> = MutableLiveData()
        initiateRequest({
            response.value = homeRepo.getArticleDetail(articleId, key)
        }, loadState, key)
        return response
    }

    /**
     * 点赞
     */
    fun articleThumbUp(articleId: String): MutableLiveData<BaseResponse<Int?>> {
        val response: MutableLiveData<BaseResponse<Int?>> = MutableLiveData()
        initiateRequest({
            response.value = homeRepo.articleThumbUp(articleId)
        }, loadState)
        return response
    }

    /**
     * 检测是否点赞
     */
    fun checkArticleThumbUp(articleId: String): MutableLiveData<BaseResponse<Int?>> {
        val response: MutableLiveData<BaseResponse<Int?>> = MutableLiveData()
        initiateRequest({
            response.value = homeRepo.checkArticleThumbUp(articleId)
        }, loadState)
        return response
    }

    /**
     * 获取收藏夹
     */
//    fun collectionList(page: Int, key: String): MutableLiveData<List<CollectionBean>> {
//        val response: MutableLiveData<List<CollectionBean>> = MutableLiveData()
//        initiateRequest({
//            val res = repository.collectionList(page, key)
//            res?.let {
//                if (it.content.isNotEmpty()) {
//                    response.value = it.content
//                }
//            }
//        }, loadState, key)
//        return response
//    }

    /**
     * 获取评论列表
     */
    fun getArticleCommentList(articleId: String, page: Int, key: String): MutableLiveData<List<CommentBean>> {
        val response: MutableLiveData<List<CommentBean>> = MutableLiveData()
        initiateRequest({
            val res = homeRepo.getArticleCommentList(articleId, page, key)
            res?.let {
                if (it.content.isNotEmpty()) {
                    response.value = it.content
                }
            }
        }, loadState)
        return response
    }

    /**
     * 获取推荐列表
     */
    fun getArticleRecommend(articleId: String, key: String): MutableLiveData<List<ArticleRecommendBean>?> {
        val response: MutableLiveData<List<ArticleRecommendBean>?> = MutableLiveData()
        initiateRequest({
            response.value = homeRepo.getArticleRecommend(articleId, 10, key)
        }, loadState)
        return response
    }

    /**
     * 回复评论
     */
    fun replyComment(comment: SubCommentInputBean): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            response.value = homeRepo.replyComment(comment)
        }, loadState)
        return response
    }

    /**
     * 评论文章
     */
    fun commentArticle(comment: CommentInputBean): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            response.value = homeRepo.commentArticle(comment)
        }, loadState)
        return response
    }

    /**
     * 打赏文章
     */
    fun priseArticle(prise: PriseArticleInputBean): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            response.value = homeRepo.priseArticle(prise)
        }, loadState)
        return response
    }

    /**
     * 打赏列表
     */
    fun getPriseArticleList(articleId: String, key: String): MutableLiveData<List<PriseArticleBean>?> {
        val response: MutableLiveData<List<PriseArticleBean>?> = MutableLiveData()
        initiateRequest({
            response.value = homeRepo.getPriseArticleList(articleId,key)
        }, loadState,key)
        return response
    }


}