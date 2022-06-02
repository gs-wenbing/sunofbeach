package com.zwb.lib_common.view

import android.app.Activity
import android.content.res.Configuration
import android.graphics.Point
import android.graphics.Rect
import android.os.Bundle
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.*
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.R
import com.zwb.lib_common.databinding.CommonDialogReplyBinding


class ReplyBottomSheetDialog(
    var activity: Activity,
    theme: Int,
) : BottomSheetDialog(activity, theme) {


    private var lastTimekeyboardHeight = 0

    private var rootView: View? = null

    lateinit var dialogBinding:CommonDialogReplyBinding

    private var keyBoardHeight = 0

    private var mOnGlobalLayoutListener: ViewTreeObserver.OnGlobalLayoutListener =
        ViewTreeObserver.OnGlobalLayoutListener {
            if (rootView != null) {
                handleOnGlobalLayout()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.decorView?.setPadding(0,0,0,0)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        window?.setGravity(Gravity.BOTTOM)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        dialogBinding = CommonDialogReplyBinding.inflate(layoutInflater)
        setContentView(dialogBinding.root)
        rootView = dialogBinding.layoutReply
        dialogBinding.editReply.isFocusable = true
        dialogBinding.editReply.isFocusableInTouchMode = true
        dialogBinding.editReply.requestFocus()
//        rootView?.viewTreeObserver?.addOnGlobalLayoutListener(mOnGlobalLayoutListener)

    }

    override fun show() {
        super.show()
        rootView?.viewTreeObserver?.addOnGlobalLayoutListener(mOnGlobalLayoutListener)
    }

    override fun dismiss() {
        dialogBinding.editReply.setText("")
        dialogBinding.tvTitle.text = "发表评论"
        super.dismiss()
    }
    private fun handleOnGlobalLayout() {
        val screenSize = Point()
        window!!.windowManager.defaultDisplay.getSize(screenSize)
        val rect = Rect()
        rootView!!.getWindowVisibleDisplayFrame(rect)

        // REMIND, you may like to change this using the fullscreen size of the phone
        // and also using the status bar and navigation bar heights of the phone to calculate
        // the keyboard height. But this worked fine on a Nexus.
        val orientation = getScreenOrientation()
        val keyboardHeight = screenSize.y - rect.bottom
        lastTimekeyboardHeight = if (lastTimekeyboardHeight == keyboardHeight) {
            return
        } else {
            keyboardHeight
        }
        when {
            keyboardHeight == 0 -> {
                notifyKeyboardHeightChanged(0, orientation)
            }
            orientation == Configuration.ORIENTATION_PORTRAIT -> {
                notifyKeyboardHeightChanged(keyboardHeight, orientation)
            }
            else -> {
                notifyKeyboardHeightChanged(keyboardHeight, orientation)
            }
        }
    }



    private fun notifyKeyboardHeightChanged(height: Int, orientation: Int) {
        keyBoardHeight = if (keyBoardHeight == height) {
            return
        } else {
            height
        }
        Log.e("======", height.toString())
        if (keyBoardHeight <= 0) { //键盘收起
            setRootPaddingBottom(UIUtils.dp2px(10f))
        } else { //键盘打开
            setRootPaddingBottom(keyBoardHeight + UIUtils.dp2px(10f) + dialogBinding.btnSend.height)
        }
    }

    private fun setRootPaddingBottom(bottom:Int){
        rootView?.setPadding(
            UIUtils.dp2px(10f),
            UIUtils.dp2px(10f),
            UIUtils.dp2px(10f),
            bottom
        )
    }
    /**
     * Get the screen orientation
     *
     * @return the screen orientation
     */
    private fun getScreenOrientation(): Int {
        return activity.resources.configuration.orientation
    }

    fun setReplyTitle(title: String){
        if(title.contains("@")){
            dialogBinding.tvTitle.text = UIUtils.setTextViewContentStyle(
                title,
                AbsoluteSizeSpan(UIUtils.dp2px(14f)),
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)),
                title.indexOf("@"), title.length
            )
        }else{
            dialogBinding.tvTitle.text = title
        }
    }

    fun sendListener(listener: OnSendListener){
        dialogBinding.btnSend.setOnClickListener {
            if(!TextUtils.isEmpty(dialogBinding.editReply.text)){
                listener.onSend(dialogBinding.editReply.text.toString())
                dialogBinding.editReply.setText("")
            }
        }
    }

    interface OnSendListener{
        fun onSend(v:String)
    }
}