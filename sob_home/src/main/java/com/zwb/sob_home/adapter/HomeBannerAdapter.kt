package com.zwb.sob_home.adapter

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.youth.banner.adapter.BannerAdapter
import com.youth.banner.util.BannerUtils
import com.zwb.sob_home.bean.BannerBean

class HomeBannerAdapter(imageUrls: List<BannerBean>) :
    BannerAdapter<BannerBean, HomeBannerAdapter.ImageHolder>(imageUrls) {


    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): ImageHolder {
        val imageView = ImageView(parent!!.context)
        val params = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        imageView.layoutParams = params
        imageView.scaleType = ImageView.ScaleType.CENTER_CROP
        //通过裁剪实现圆角
        BannerUtils.setBannerRound(imageView, 20f)
        return ImageHolder(imageView)
    }


    class ImageHolder(view: View) : RecyclerView.ViewHolder(view) {
        var imageView: ImageView = view as ImageView
    }

    override fun onBindView(
        holder: ImageHolder?,
        data: BannerBean?,
        position: Int,
        size: Int
    ) {
        Glide.with(holder!!.itemView)
            .asBitmap()
            .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
            .load(data!!.picUrl)
            .into(holder.imageView)
    }

}