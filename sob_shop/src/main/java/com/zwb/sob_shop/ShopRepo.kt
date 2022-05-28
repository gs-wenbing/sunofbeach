package com.zwb.sob_shop

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.dataConvert
import com.zwb.lib_base.mvvm.m.BaseRepository
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.net.State
import com.zwb.lib_common.bean.TokenBean
import com.zwb.sob_shop.bean.*

class ShopRepo(private val loadState: MutableLiveData<State>) : BaseRepository() {
    private val apiService by lazy {
        RetrofitFactory.instance.getService(ShopApi::class.java, ShopApi.BASE_URL)
    }

    suspend fun checkToken(key: String): TokenBean? {
        return apiService.checkToken().dataConvert(loadState, key)
    }

    suspend fun discoveryCategoryList(key: String): List<DiscoverCategoryBean>? {
        return apiService.discoveryCategoryList().dataConvert(loadState, key)
    }

    suspend fun recommendCategoryList(key: String): List<RecommendCategoriesBean>? {
        return apiService.recommendCategoryList().dataConvert(loadState, key)
    }

    suspend fun getDiscoveryByCategoryId(
        categoryId: Long,
        page: Int,
        key: String
    ): List<DiscoverGoodsBean>? {
        return apiService.getDiscoveryByCategoryId(categoryId, page).dataConvert(loadState, key)
    }

    suspend fun getRecommendByCategoryId(categoryId: Long, key: String): RecommendBean? {
        return apiService.getRecommendByCategoryId(categoryId).dataConvert(loadState, key)
    }

    suspend fun goodsRelativeList(categoryId: Long, key: String): RelativeBean? {
        return apiService.goodsRelativeList(categoryId).dataConvert(loadState, key)
    }


    suspend fun tpwd(body: TpwdInputBean): BaseResponse<TbkTpwdResponse?> {
        return apiService.tpwd(body)
    }


}