package com.zwb.sob_home

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.bean.ListData
import com.zwb.lib_base.bean.PageViewData
import com.zwb.lib_base.ktx.dataConvert
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.net.State
import com.zwb.lib_common.CommonRepo
import com.zwb.lib_common.bean.CollectInputBean
import com.zwb.sob_home.bean.*

class HomeRepo(private val loadState: MutableLiveData<State>) : CommonRepo(loadState) {
    private val apiService by lazy {
        RetrofitFactory.instance.getService(HomeApi::class.java, HomeApi.BASE_URL)
    }

    suspend fun categoryList(key: String): List<CategoryBean>? {
        return apiService.categoryList().dataConvert(loadState, key)
    }

    suspend fun getRecommendByCategoryId(
        categoryId: String,
        page: Int,
        key: String
    ): ListData<HomeItemBean>? {
        return apiService.getRecommendByCategoryId(categoryId, page).dataConvert(loadState, key)
    }

    suspend fun getRecommend(page: Int, key: String): ListData<HomeItemBean>? {
        return apiService.getRecommend(page).dataConvert(loadState, key)
    }

    suspend fun getBanner(key: String): List<BannerBean>? {
        return apiService.getBanner().dataConvert(loadState, key)
    }

    suspend fun getArticleDetail(articleId: String, key: String): ArticleDetailBean? {
        return apiService.getArticleDetail(articleId).dataConvert(loadState, key)
    }

    suspend fun articleThumbUp(articleId: String): BaseResponse<Int?> {
        return apiService.articleThumbUp(articleId)
    }

    suspend fun checkArticleThumbUp(articleId: String): BaseResponse<Int?> {
        return apiService.checkArticleThumbUp(articleId)
    }

//    suspend fun collectionList(page: Int, key: String): PageViewData<CollectionBean>? {
//        return apiService.collectionList(page).dataConvert(loadState,key)
//    }


    suspend fun getArticleRecommend(articleId: String, size: Int, key: String): List<ArticleRecommendBean>? {
        return apiService.getArticleRecommend(articleId,size).dataConvert(loadState,key)
    }

    suspend fun getArticleCommentList(articleId: String, page: Int, key: String): PageViewData<CommentBean>? {
        return apiService.getArticleCommentList(articleId,page).dataConvert(loadState,key)
    }

    suspend fun commentArticle(comment: CommentInputBean): BaseResponse<String?> {
        return apiService.commentArticle(comment)
    }



}