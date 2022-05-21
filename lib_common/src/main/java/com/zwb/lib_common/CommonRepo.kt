package com.zwb.lib_common

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.bean.PageViewData
import com.zwb.lib_base.ktx.dataConvert
import com.zwb.lib_base.mvvm.m.BaseRepository
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.net.State
import com.zwb.lib_common.bean.CollectInputBean
import com.zwb.lib_common.bean.CollectionBean
import com.zwb.lib_common.bean.PriseQrCode
import com.zwb.lib_common.bean.TokenBean

open class CommonRepo(private val loadState: MutableLiveData<State>) : BaseRepository() {

    private val apiService by lazy {
        RetrofitFactory.instance.getService(CommonApi::class.java, CommonApi.BASE_URL)
    }

    suspend fun checkToken(key: String): TokenBean? {
        return apiService.checkToken().dataConvert(loadState, key)
    }

    suspend fun getPriseQrCode(userId: String): BaseResponse<PriseQrCode?> {
        return apiService.getPriseQrCode(userId)
    }

    suspend fun collectionList(page: Int, key: String): PageViewData<CollectionBean>? {
        return apiService.collectionList(page).dataConvert(loadState, key)
    }


    suspend fun checkCollected(url: String): BaseResponse<String?> {
        return apiService.checkCollected(url)
    }

    suspend fun favorite(body: CollectInputBean): BaseResponse<String?> {
        return apiService.favorite(body)
    }

    suspend fun unFavorite(favoriteId: String): BaseResponse<String?> {
        return apiService.unFavorite(favoriteId)
    }

    suspend fun follow(userId: String): BaseResponse<Int?> {
        return apiService.follow(userId)
    }

    suspend fun unFollow(userId: String): BaseResponse<Int?> {
        return apiService.unFollow(userId)
    }

    suspend fun followState(userId: String): BaseResponse<Int?> {
        return apiService.followState(userId)
    }



}