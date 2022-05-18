package com.zwb.sob_shop

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.dataConvert
import com.zwb.lib_base.mvvm.m.BaseRepository
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.net.State
import com.zwb.lib_common.bean.TokenBean
import com.zwb.sob_shop.bean.DiscoverCategoryBean
import com.zwb.sob_shop.bean.DiscoverGoodsBean
import com.zwb.sob_shop.bean.RecommendCategoriesBean
import com.zwb.sob_shop.bean.ShopCategoryBean

class ShopRepo(private val loadState: MutableLiveData<State>) :BaseRepository() {
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

    suspend fun getDiscoveryByCategoryId(materialId: Long, page: Int, key: String): List<DiscoverGoodsBean>? {
        return apiService.getDiscoveryByCategoryId(materialId, page).dataConvert(loadState, key)
    }
}