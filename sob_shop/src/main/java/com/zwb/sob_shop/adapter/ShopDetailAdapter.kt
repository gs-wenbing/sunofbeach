package com.zwb.sob_shop.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.sob_shop.R
import com.zwb.sob_shop.bean.IGoodsItem

class ShopDetailAdapter(data: MutableList<IGoodsItem>?) :
    BaseQuickAdapter<IGoodsItem, BaseViewHolder>(R.layout.shop_adapter_goods, data) {

    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: IGoodsItem?) {
        item?.let {
            helper.setText(R.id.tv_goods_name, item.getGoodsTitle())
            val tvOldPrice = helper.getView<TextView>(R.id.tv_old_price)
            val ivGoodsImage = helper.getView<ImageView>(R.id.iv_goods_image)
            BannerUtils.setBannerRound(ivGoodsImage, UIUtils.dp2px(5f).toFloat())
            Glide.with(ivGoodsImage.context)
                .load(item.getPicUrl())
                .placeholder(R.drawable.shape_grey_background)
                .into(ivGoodsImage)
            tvOldPrice.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            tvOldPrice.text = "原价 ${item.getOriginPrice()}"
            helper.setText(R.id.tv_trade_price, "券后价 ${String.format("%.2f",(item.getOriginPrice() - item.getCouponAmount()))}")
            helper.setText(R.id.tv_volume_count, item.getSaleNum().toString())
            helper.addOnClickListener(R.id.btn_coupon)
        }
    }

}