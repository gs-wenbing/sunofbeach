package com.zwb.lib_common.view

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.PopupWindow
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class FixedHeightBottomSheetDialog(
    context: Context,
    theme: Int,
    private val fixedHeight: Int
) : BottomSheetDialog(context, theme) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPeekHeight(fixedHeight)
        setMaxHeight(fixedHeight)
    }

    private fun setPeekHeight(peekHeight: Int) {
        if (peekHeight <= 0) {
            return
        }
        val bottomSheetBehavior = getBottomSheetBehavior()
        bottomSheetBehavior?.peekHeight = peekHeight
    }

    private fun setMaxHeight(maxHeight: Int) {
        if (maxHeight <= 0) {
            return
        }
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, maxHeight)
        window?.setGravity(Gravity.BOTTOM)
        window?.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)
    }

    private fun getBottomSheetBehavior(): BottomSheetBehavior<View>? {
        val view: View? = window?.findViewById(com.google.android.material.R.id.design_bottom_sheet)
        return view?.let { BottomSheetBehavior.from(view) }
    }
}