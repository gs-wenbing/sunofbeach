package com.zwb.sob_shop

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_base.net.BaseResponse
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
    ): MutableLiveData<List<IGoodsItem>?> {
        val response: MutableLiveData<List<IGoodsItem>?> = MutableLiveData()
        initiateRequest({
            if (mainTab == MAIN_TAB_1) {
                response.value = repository.getDiscoveryByCategoryId(categoryId, page, key)
            } else {
                val res = repository.getRecommendByCategoryId(categoryId, key)
                response.value = res?.tbk_dg_optimus_material_response?.result_list?.map_data
            }
        }, loadState, key)
        return response
    }


    fun goodsRelativeList(
        goodsId: Long,
        key: String
    ): MutableLiveData<RelativeBean?> {
        val response: MutableLiveData<RelativeBean? > = MutableLiveData()
        initiateRequest({
            response.value = repository.goodsRelativeList(goodsId, key)
        }, loadState, key)
        return response
    }

    fun tpwd(body: TpwdInputBean): MutableLiveData<BaseResponse<TbkTpwdResponse?>> {
        val response: MutableLiveData<BaseResponse<TbkTpwdResponse?>> = MutableLiveData()
        initiateRequest({
            response.value = repository.tpwd(body)
        }, loadState)
        return response
    }

}