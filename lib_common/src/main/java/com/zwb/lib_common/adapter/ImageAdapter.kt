package com.zwb.lib_common.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.ktx.height
import com.zwb.lib_base.ktx.width
import com.zwb.lib_common.R


class ImageAdapter(var width: Int, data: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.common_moyu_adapter_image, data) {


    override fun convert(helper: BaseViewHolder, item: String?) {
        item?.let {
            val ivImage: ImageView = helper.getView(R.id.iv_image)
            ivImage.width(width)
            ivImage.height(width)
            BannerUtils.setBannerRound(ivImage, 10f)
            Glide.with(mContext).load(it).placeholder(R.drawable.shape_grey_background)
                .into(ivImage)
        }
    }

}