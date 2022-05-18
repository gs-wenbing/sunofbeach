package com.zwb.sob_shop

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_common.bean.TokenBean
import com.zwb.sob_shop.bean.*
import com.zwb.sob_shop.fragment.ShopMainFragment.Companion.MAIN_TAB_1

class ShopViewModel : BaseViewModel() {

    private val repository by lazy {
        ShopRepo(loadState)
    }

    fun checkToken(key: String): MutableLiveData<TokenBean?> {
        val response: MutableLiveData<TokenBean?> = MutableLiveData()
        initiateRequest({
            response.value = repository.checkToken(key)
        }, loadState, key)
        return response
    }


    fun categoryList(mainTab: String, key: String): MutableLiveData<List<ShopCategoryBean>?> {
        val response: MutableLiveData<List<ShopCategoryBean>?> = MutableLiveData()
        initiateRequest({
            if (mainTab == MAIN_TAB_1) {
                response.value = repository.discoveryCategoryList(key)
            } else {
                response.value = repository.recommendCategoryList(key)
            }
        }, loadState, key)
        return response
    }


    fun shopGoodsList(
        mainTab: String,
        categoryId: Long,
        page: Int,
        key: String
    ): MutableLiveData<List<ShopItemGoodsBean>?> {
        val response: MutableLiveData<List<ShopItemGoodsBean>?> = MutableLiveData()
        initiateRequest({
            if (mainTab == MAIN_TAB_1) {
                response.value = repository.getDiscoveryByCategoryId(categoryId, page, key)
            } else {
                response.value = mutableListOf()
            }
        }, loadState, key)
        return response
    }


//    fun getDiscoveryByCategoryId(
//        materialId: String,
//        page: Int,
//        key: String
//    ): MutableLiveData<List<DiscoverGoodsBean>?> {
//        val response: MutableLiveData<List<DiscoverGoodsBean>?> = MutableLiveData()
//        initiateRequest({
//            response.value = repository.getDiscoveryByCategoryId(materialId, page, key)
//        }, loadState, key)
//        return response
//    }
}