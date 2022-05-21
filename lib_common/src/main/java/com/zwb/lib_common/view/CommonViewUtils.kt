package com.zwb.lib_common.view

import android.content.Context
import android.content.res.ColorStateList
import android.text.Html
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.R
import net.mikaelzero.mojito.Mojito
import net.mikaelzero.mojito.ext.mojito
import net.mikaelzero.mojito.impl.DefaultPercentProgress
import net.mikaelzero.mojito.impl.NumIndicator

object CommonViewUtils {

    fun setFollowState(btn: TextView, state: Int) {
        when (state) {
            0 -> {
                btn.text = "+ 关注"
                btn.setTextColor(ContextCompat.getColor(btn.context, R.color.colorAccent))
                btn.setBackgroundResource(R.drawable.blue_hollow_btn_selector)
            }
            1 -> {
                btn.text = "回粉"
                btn.setTextColor(ContextCompat.getColor(btn.context, R.color.white))
                btn.setBackgroundResource(R.drawable.red_solid_btn_selector)
            }
            2 -> {
                btn.text = "已关注"
                btn.setTextColor(ContextCompat.getColor(btn.context, R.color.white))
                btn.setBackgroundResource(R.drawable.green_solid_btn_selector)
            }
            3 -> {
                btn.text = "相互关注"
                btn.setTextColor(ContextCompat.getColor(btn.context, R.color.white))
                btn.setBackgroundResource(R.drawable.blue_solid_btn_selector)
            }
        }
    }

    fun setHtml(tvContent: TextView, content: String) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            tvContent.text = Html.fromHtml(
                content, Html.FROM_HTML_MODE_LEGACY,
                HtmlImageGetter(tvContent),
                null
            )
        } else {
            tvContent.text = Html.fromHtml(content)
        }
    }

    fun setThumbStyle(view:TextView,isThumb:Boolean){
        if(isThumb){
            view.setTextColor(ContextCompat.getColor(view.context,R.color.colorPrimary))
            val drawable = ContextCompat.getDrawable(view.context,R.mipmap.ic_likes_checked)
            drawable!!.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            view.setCompoundDrawables(drawable, null, null, null)
        }else{
            view.setTextColor(ContextCompat.getColor(view.context,R.color.icon_color))
            val drawable = ContextCompat.getDrawable(view.context,R.mipmap.ic_likes)
            drawable!!.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            view.setCompoundDrawables(drawable, null, null, null)
        }
    }
    fun showBigImage(recyclerView: RecyclerView, @IdRes viewId: Int, pics: List<String>, position: Int) {
        recyclerView.mojito(viewId) {
            urls(pics)
            position(position)
            mojitoListener(
                onClick = { view, x, y, pos ->
//                        Toast.makeText(context, "tap click", Toast.LENGTH_SHORT).show()
                }
            )
            progressLoader {
                DefaultPercentProgress()
            }
            setIndicator(NumIndicator())
        }
    }

    fun showBigImage(view: View, url:String?){
        url?.let {
            view.mojito(url){
                mojitoListener(
                    onClick = { view, x, y, pos ->
                    }
                )
                progressLoader {
                    DefaultPercentProgress()
                }
            }
        }
    }
    fun showBigImage(context: Context, urls:List<String>,position: Int){
        Mojito.start(context) {
            urls(urls)
            position(position)
            mojitoListener(
                onClick = { view, x, y, pos ->
                }
            )
            progressLoader {
                DefaultPercentProgress()
            }
        }
    }
}