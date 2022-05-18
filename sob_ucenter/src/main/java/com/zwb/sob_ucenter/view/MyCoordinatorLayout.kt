package com.zwb.sob_ucenter.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import kotlin.math.abs

class MyCoordinatorLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    private var mListener:OnScrollListener?=null

    override fun onNestedPreScroll(target: View, dx: Int, dy: Int, consumed: IntArray, type: Int) {
        super.onNestedPreScroll(target, dx, dy, consumed, type)
        // 第一个子view的到顶部的距离
        val currentTop: Int = getChildAt(0).top
        // currentTop 最大值是0
        // 向上滚动  值变小
        // 向下滚动  值变大直到0
        mListener?.onScroll(currentTop)
    }

    override fun onTouchEvent(ev: MotionEvent): Boolean {

        return super.onTouchEvent(ev)
    }

    override fun computeVerticalScrollOffset(): Int {
        val aa = super.computeVerticalScrollOffset()
        Log.e("scrollY2222===",aa.toString())
        return aa
    }
    interface OnScrollListener{
        fun onScroll(scrollY: Int)
    }
    fun setOnScrollListener(listener: OnScrollListener){
        mListener = listener
    }
}