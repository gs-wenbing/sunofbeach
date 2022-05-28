package com.zwb.sob_shop.bean

interface IGoodsItem{
    fun getGoodsId():Long
    // 标题
    fun getGoodsTitle():String
    // 原价
    fun getOriginPrice():Double
    // 券金额
    fun getCouponAmount():Double
    // 多少人购买
    fun getSaleNum():Int
    // 图片列表
    fun getSmallImages():List<String>
    // 图片
    fun getPicUrl():String

    fun getCouponUrl():String
}