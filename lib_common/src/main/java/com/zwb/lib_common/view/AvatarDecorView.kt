package com.zwb.lib_common.view

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.content.ContextCompat
import androidx.core.graphics.scaleMatrix
import com.bumptech.glide.Glide
import com.zwb.lib_common.R
import kotlin.math.min

class AvatarDecorView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : AppCompatImageView(context, attrs) {

    // 默认头像 drawable
    private val defAvatarDrawable = ContextCompat.getDrawable(context, R.mipmap.ic_default_avatar)

    // 边框 drawable
    private val decorDrawable = GradientDrawable().apply {
        shape = GradientDrawable.OVAL
        setStroke(6, ContextCompat.getColor(context, R.color.colorVip2))
    }

    // 前景徽章图标的画笔
    private val badgePaint = Paint(Paint.ANTI_ALIAS_FLAG)

    // 小徽章 bitmap
    private var badgeBitmap = BitmapFactory.decodeResource(context.resources, R.mipmap.ic_vip)

    // 缩放因子
    private val scaleFactor = 0.38f

    // 是否为 VIP
    private var isVip: Boolean = false

    init {
        // 如果 xml 里没有设置资源图片，则设置默认的头像 drawable
        if (drawable == null) setImageDrawable(defAvatarDrawable)
    }

    /**
     * 加载头像（剪裁成圆形）
     * 参数：
     * 1、是否为 VIP
     * 2、资源
     * 3、边框的自定义配置
     */
    fun loadAvatar(vip: Boolean = false, resource: Any?, block: GradientDrawable.() -> Unit = {}) {
        isVip = vip
        block.invoke(decorDrawable)
        Glide.with(this)
            .load(resource)
            .placeholder(R.mipmap.ic_default_avatar)
            .error(R.mipmap.ic_default_avatar)
            .circleCrop()
            .into(this)
    }

    /**
     * 使用 Glide 加载 ImageView 图片时会调用此方法，我们可以在这里对设置的 drawable 资源进行修饰。
     */
    override fun setImageDrawable(drawable: Drawable?) {
        val decorDrawable = if (isVip && drawable != null) {
            // 如果是 VIP 且 drawable 资源不为空，则添加 decorDrawable
            LayerDrawable(arrayOf(drawable, decorDrawable))
        } else {
            // 如果设置的头像 drawable 为空，则设置默认头像 drawable
            drawable ?: defAvatarDrawable
        }
        super.setImageDrawable(decorDrawable)
    }

    /**
     * 绘制前景徽章图标
     */
    override fun onDrawForeground(canvas: Canvas?) {
        super.onDrawForeground(canvas)
        canvas ?: return
        if (isVip) {
            val scale = min(scaleFactor * width / badgeBitmap.width,1f)
            Log.e("=====",scale.toString())
            val dx = 1f * width - badgeBitmap.width * scale
            val dy = 1f * height - badgeBitmap.height * scale
            // 画布默认是在左上角，需要先调整画布的位置
            canvas.translate(dx, dy)
            // 绘制小徽章
            canvas.drawBitmap(badgeBitmap, scaleMatrix(scale,scale), badgePaint)
        }
    }
}