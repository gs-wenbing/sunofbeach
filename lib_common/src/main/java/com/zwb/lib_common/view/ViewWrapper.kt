package com.zwb.lib_common.view

import android.view.View
import com.zwb.lib_base.ktx.height
import com.zwb.lib_base.ktx.width

class ViewWrapper(private val rView: View) {

    fun getWidth(): Int {
        return rView.layoutParams.width
    }

    fun setWidth(width: Int) {
        rView.width(width)
        rView.requestLayout()
    }

    fun getHeight(): Int {
        return rView.layoutParams.height
    }

    fun setHeight(height: Int) {
        rView.height(height)
    }

}