package com.zwb.lib_common.view

import android.R
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.LevelListDrawable
import android.text.Html
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition

/**
 * TextView显示HTML的图片
 */
class HtmlImageGetter(var textView: TextView): Html.ImageGetter {
    override fun getDrawable(source: String): Drawable {
        //在getDrawable中的source就是 img标签里src的值也就是图片的路径
        val drawable = LevelListDrawable()

        val target:CustomTarget<Bitmap> =object :CustomTarget<Bitmap>(){
            override fun onResourceReady(bitmap: Bitmap, transition: Transition<in Bitmap>?) {
                val bitmapDrawable = BitmapDrawable(textView.resources, bitmap)
                drawable.addLevel(1, 1, bitmapDrawable)
                drawable.setBounds(0, 0, bitmap.width, bitmap.height)
                drawable.level = 1
                textView.invalidate()
                textView.text = textView.text //解决图文重叠
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                super.onLoadFailed(placeholder);
            }
        }
        Glide.with(textView.context)
            .asBitmap()
            .load(source)
            .into(target)
        return drawable;
    }
}