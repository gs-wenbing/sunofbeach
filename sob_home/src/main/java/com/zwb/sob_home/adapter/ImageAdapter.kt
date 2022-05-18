package com.zwb.sob_home.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.ktx.height
import com.zwb.lib_base.ktx.width
import com.zwb.lib_base.utils.UIUtils
import com.zwb.sob_home.R

class ImageAdapter(var w:Int, data: MutableList<String>?) :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.home_adapter_image, data) {

    override fun convert(helper: BaseViewHolder, item: String?) {
        item?.let {
            val ivImage: ImageView = helper.getView(R.id.iv_image)
            ivImage.width(w)
            ivImage.height(w)
            BannerUtils.setBannerRound(ivImage, 10f)
            Glide.with(ivImage.context)
                .load(it)
                .placeholder(R.drawable.shape_grey_background)
                .override(w)
                .thumbnail(0.3f)
                .into(ivImage)
        }
    }

}