package com.zwb.sob_shop

import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_common.bean.TokenBean
import com.zwb.lib_common.constant.Constants
import com.zwb.sob_shop.bean.DiscoverCategoryBean
import com.zwb.sob_shop.bean.DiscoverGoodsBean
import com.zwb.sob_shop.bean.RecommendCategoriesBean
import retrofit2.http.GET
import retrofit2.http.Path

interface ShopApi {

    @GET(Constants.URL.CHECK_TOKEN_URL)
    suspend fun checkToken(): BaseResponse<TokenBean?>

    @GET(DISCOVERY_CATEGORIES_URL)
    suspend fun discoveryCategoryList(): BaseResponse<List<DiscoverCategoryBean>?>

    @GET("${DISCOVERY_LIST_URL}/{materialId}/{page}")
    suspend fun getDiscoveryByCategoryId(
        @Path("materialId") materialId: Long,
        @Path("page") page: Int
    ): BaseResponse<List<DiscoverGoodsBean>?>


    @GET(RECOMMEND_CATEGORIES_URL)
    suspend fun recommendCategoryList(): BaseResponse<List<RecommendCategoriesBean>?>

//    @GET("${RECOMMEND_LIST_URL}/{materialId}/{page}")
//    suspend fun getRecommendByCategoryId(
//        @Path("materialId") materialId: Long,
//        @Path("page") page: Int
//    ): BaseResponse<ListData<DiscoverGoodsBean>?>

    companion object {
        const val BASE_URL = "https://api.sunofbeaches.com/"

        // 获取发现页的分类
        const val DISCOVERY_CATEGORIES_URL = "shop/discovery/categories"

        // 根据发现页分类ID获取分类内容
        const val DISCOVERY_LIST_URL = "shop/discovery"

        // 生成淘口令 tpwd
        const val TPWD_URL = "shop/tpwd"

        // 获取相关商品 post goodsId 商品ID
        const val GOODS_RELATIVE_URL = "shop/goods/relative"

        // 获取精选页(券场)的分类
        const val RECOMMEND_CATEGORIES_URL = "shop/recommend/categories"

        // 根据精选页(券场)的分类获取精选内容
        const val RECOMMEND_LIST_URL = "shop/recommend"


    }
}