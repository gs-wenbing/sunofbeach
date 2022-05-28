package com.zwb.sob_shop.bean

data class RelativeBean(
    val tbk_item_recommend_get_response: TbkItemRecommendGetResponse?
)

data class TbkItemRecommendGetResponse(
    val request_id: String,
    val results: RelativeResults?
)

data class RelativeResults(
    val n_tbk_item: List<NTbkItem>?
)

data class NTbkItem(
    val item_url: String,
    val num_iid: Long,
    val pict_url: String,
    val provcity: String,
    val reserve_price: Double,
    val small_images: SmallImages?,
    val title: String,
    val user_type: Int,
    val zk_final_price: Double?
):ShopItemGoodsBean(),IGoodsItem{
    override fun getGoodsId(): Long {
        return num_iid
    }

    override fun getGoodsTitle(): String {
        return title
    }

    override fun getOriginPrice(): Double {
        return zk_final_price?:reserve_price
    }

    override fun getCouponAmount(): Double {
        return 0.0
    }

    override fun getSaleNum(): Int {
        return 0
    }

    override fun getSmallImages(): List<String> {
        val list = mutableListOf<String>()
        small_images?.string?.forEach {
            list.add("https:$it")
        }
        return if (list.isNotEmpty()) list else mutableListOf(getPicUrl())
    }

    override fun getPicUrl(): String {
        return "https:$pict_url"
    }

    override fun getCouponUrl(): String {
        return item_url ?: ""
    }
}

