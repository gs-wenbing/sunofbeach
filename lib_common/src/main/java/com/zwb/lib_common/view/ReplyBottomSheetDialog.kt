package com.zwb.lib_common.view

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.text.Selection
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.Gravity
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDialog
import androidx.core.content.ContextCompat
import androidx.core.widget.doAfterTextChanged
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.R
import com.zwb.lib_common.databinding.CommonDialogReplyBinding


class ReplyBottomSheetDialog(var activity: Activity) :
    AppCompatDialog(activity, R.style.BaseDialog) {

    private val maxNum = 520
    private lateinit var dialogBinding: CommonDialogReplyBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.decorView?.setPadding(0, 0, 0, 0)
        window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        window?.setGravity(Gravity.BOTTOM)
        window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE or WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        dialogBinding = CommonDialogReplyBinding.inflate(layoutInflater)
        setContentView(dialogBinding.root)
        dialogBinding.editReply.isFocusable = true
        dialogBinding.editReply.isFocusableInTouchMode = true
        dialogBinding.editReply.requestFocus()
        dialogBinding.editReply.doAfterTextChanged {
            it?.run {
                if (this.length > maxNum) {
                    dialogBinding.editReply.setText(this.subSequence(0, maxNum))
                    Selection.setSelection(dialogBinding.editReply.text, maxNum)
                }
                dialogBinding.tvTextNum.text = "${dialogBinding.editReply.text.length}/$maxNum"
            }
        }

    }


    override fun dismiss() {
        dialogBinding.editReply.setText("")
        dialogBinding.tvTitle.text = "发表评论"
        super.dismiss()
    }

    fun setReplyTitle(title: String) {
        if (title.contains("@")) {
            dialogBinding.tvTitle.text = UIUtils.setTextViewContentStyle(
                title,
                AbsoluteSizeSpan(UIUtils.dp2px(14f)),
                ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary)),
                title.indexOf("@"), title.length
            )
        } else {
            dialogBinding.tvTitle.text = title
        }
    }

    fun sendListener(listener: OnSendListener) {
        dialogBinding.btnSend.setOnClickListener {
            if (!TextUtils.isEmpty(dialogBinding.editReply.text)) {
                listener.onSend(dialogBinding.editReply.text.toString())
                dialogBinding.editReply.setText("")
            }
        }
    }

    interface OnSendListener {
        fun onSend(v: String)
    }
}