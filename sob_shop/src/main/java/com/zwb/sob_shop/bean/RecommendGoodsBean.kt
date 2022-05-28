package com.zwb.sob_shop.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * {
"category_id":50013090,
"click_url":"//s.click.taobao.com/t?e=m%3D2%26s%3DAUbIyFQkXRBw4vFB6t2Z2ueEDrYVVa64Dne87AjQPk9yINtkUhsv0LRKL2QgeGsCaSFbOJbYh6xm5nS0VR3oT0KAZCke%2BMGJxC%2FP4%2FZfPFbcQmwDRwHnn1oN8CPq4PKMZiqtwk9j5QNerBM5mSXVLT9hbWdn3uWm3bk9c5uYdcKPw4QAKVC09mhkR7hxsO42SdChf3U3iXY%2B5QowgvHJPA%3D%3D&amp;union_lens=lensId%3AMAPI%401653447431%402133f16b_08f4_180f9259565_78c4%4001",
"commission_rate":"0.6",
"coupon_amount":5,
"coupon_click_url":"//uland.taobao.com/coupon/edetail?e=9Rqg%2BbfvXE8NfLV8niU3R5TgU2jJNKOfNNtsjZw%2F%2FoIO%2FhozfxeQu240Az3WdIDNDiIi7GjFMug%2FZStw2JAdeMuRTiT9oEhVZV8pr6FWc0MC37IJt91u%2Fret%2FR41bXFkmMHpNfYdHdBwWfUaU7r%2BdMHdg8oYVc%2FsB3IEI%2FtGZdTSBjM3vXy9T041UyeSsrqpDNJ7jXMyFwklM1ZJHcLCJg%3D%3D&amp;&amp;app_pvid=59590_33.51.241.107_830_1653447431421&amp;ptl=floorId:31539;app_pvid:59590_33.51.241.107_830_1653447431421;tpp_pvid:&amp;union_lens=lensId%3AMAPI%401653447431%402133f16b_08f4_180f9259565_78c4%4001",
"coupon_end_time":"1656604799000",
"coupon_info":"满12元减5元",
"coupon_remain_count":99052,
"coupon_share_url":"//uland.taobao.com/coupon/edetail?e=9Rqg%2BbfvXE8NfLV8niU3R5TgU2jJNKOfNNtsjZw%2F%2FoIO%2FhozfxeQu240Az3WdIDNDiIi7GjFMug%2FZStw2JAdeMuRTiT9oEhVZV8pr6FWc0MC37IJt91u%2Fret%2FR41bXFkmMHpNfYdHdBwWfUaU7r%2BdMHdg8oYVc%2FsB3IEI%2FtGZdTSBjM3vXy9T041UyeSsrqpDNJ7jXMyFwklM1ZJHcLCJg%3D%3D&amp;&amp;app_pvid=59590_33.51.241.107_830_1653447431421&amp;ptl=floorId:31539;app_pvid:59590_33.51.241.107_830_1653447431421;tpp_pvid:&amp;union_lens=lensId%3AMAPI%401653447431%402133f16b_08f4_180f9259565_78c4%4001",
"coupon_start_fee":"12.0",
"coupon_start_time":"1651939200000",
"coupon_total_count":100000,
"item_id":598858489420,
"level_one_category_id":50002766,
"nick":"卡滋乐旗舰店",
"pict_url":"//img.alicdn.com/i1/1771578835/O1CN014bvqxn2F8TqCXmElo_!!1771578835-0-lubanu-s.jpg",
"reserve_price":"12.8",
"seller_id":1771578835,
"shop_title":"卡滋乐旗舰店",
"small_images":{
"string":[
"//img.alicdn.com/i2/1771578835/O1CN01dX0kMZ2F8ToiqtqeL_!!1771578835.jpg",
"//img.alicdn.com/i1/1771578835/O1CN01nm6X8h2F8Tokiy4ni_!!1771578835.jpg",
"//img.alicdn.com/i4/1771578835/O1CN01YARiS12F8ToMCjrAR_!!1771578835.jpg",
"//img.alicdn.com/i1/1771578835/O1CN01pvzygF2F8Ton3nFVq_!!1771578835.jpg"
]
},
"title":"综合什锦果蔬脆片蔬菜干水果干零食混合装脱水即食香菇秋葵脆袋装",
"user_type":1,
"volume":400,
"white_image":"https://img.alicdn.com/bao/uploaded/TB1ChQkvNv1gK0jSZFFSuv0sXXa.jpg",
"zk_final_price":"12.8"
}
 */
data class RecommendGoodsBean(
    val category_id: Long,
    val click_url: String,
    val commission_rate: String,
    val coupon_amount: Double,
    val coupon_click_url: String?,
    val coupon_end_time: String?,
    val coupon_info: String,
    val coupon_remain_count: Int,
    val coupon_share_url: String?,
    val coupon_start_fee: String,
    val coupon_start_time: String?,
    val coupon_total_count: Int,
    val item_id: Long,
    val level_one_category_id: Long,
    val nick: String?,
    val pict_url: String,
    val reserve_price: Double,
    val seller_id: Long,
    val shop_title: String?,
    val small_images: SmallImages?,
    val title: String,
    val user_type: Int,
    val volume: Int,
    val white_image: String?,
    val zk_final_price: Double?
) : IGoodsItem, ShopItemGoodsBean(), Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readLong(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readInt(),
        parcel.readLong(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readString() ?: "",
        parcel.readDouble(),
        parcel.readLong(),
        parcel.readString(),
        parcel.readParcelable(SmallImages::class.java.classLoader),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readValue(Double::class.java.classLoader) as? Double
    ) {
    }

    override fun getGoodsId(): Long {
        return item_id
    }

    override fun getGoodsTitle(): String {
        return title
    }

    override fun getOriginPrice(): Double {
        return zk_final_price ?: reserve_price
    }

    override fun getCouponAmount(): Double {
        return coupon_amount
    }

    override fun getSaleNum(): Int {
        return volume
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
        return coupon_click_url ?: click_url
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(category_id)
        parcel.writeString(click_url)
        parcel.writeString(commission_rate)
        parcel.writeDouble(coupon_amount)
        parcel.writeString(coupon_click_url)
        parcel.writeString(coupon_end_time)
        parcel.writeString(coupon_info)
        parcel.writeInt(coupon_remain_count)
        parcel.writeString(coupon_share_url)
        parcel.writeString(coupon_start_fee)
        parcel.writeString(coupon_start_time)
        parcel.writeInt(coupon_total_count)
        parcel.writeLong(item_id)
        parcel.writeLong(level_one_category_id)
        parcel.writeString(nick)
        parcel.writeString(pict_url)
        parcel.writeDouble(reserve_price)
        parcel.writeLong(seller_id)
        parcel.writeString(shop_title)
        parcel.writeParcelable(small_images, flags)
        parcel.writeString(title)
        parcel.writeInt(user_type)
        parcel.writeInt(volume)
        parcel.writeString(white_image)
        parcel.writeValue(zk_final_price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RecommendGoodsBean> {
        override fun createFromParcel(parcel: Parcel): RecommendGoodsBean {
            return RecommendGoodsBean(parcel)
        }

        override fun newArray(size: Int): Array<RecommendGoodsBean?> {
            return arrayOfNulls(size)
        }
    }

}

