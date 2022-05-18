package com.zwb.sob_home.bean

import com.chad.library.adapter.base.entity.MultiItemEntity

data class BannerList(
    var bannerList: List<BannerBean>
) : MultiItemEntity {
    override fun getItemType(): Int {
        return 100
    }
}
