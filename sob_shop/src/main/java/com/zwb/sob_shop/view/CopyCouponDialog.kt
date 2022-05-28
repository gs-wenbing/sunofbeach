package com.zwb.sob_shop.view

import android.app.Dialog
import android.content.Context
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import androidx.annotation.NonNull
import com.zwb.sob_shop.R
import com.zwb.sob_shop.databinding.ShopDialogCopyCouponBinding

class CopyCouponDialog(@NonNull context: Context) : Dialog(context, R.style.shop_dialog_style) {

    private val binding = ShopDialogCopyCouponBinding.inflate(layoutInflater)

    init {
        window?.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        window?.setFlags(
            WindowManager.LayoutParams.SCREEN_ORIENTATION_CHANGED,
            WindowManager.LayoutParams.SCREEN_ORIENTATION_CHANGED
        )
        setContentView(binding.root)
        val lp = window?.attributes
        // 获取屏幕宽、高用
        val d: DisplayMetrics = context.resources.displayMetrics
        // 高度设置为屏幕的0.7
        lp?.width = (d.widthPixels * 0.8).toInt()
        // dimAmount在0.0f和1.0f之间，0.0f完全不暗，1.0f全暗
        lp?.dimAmount = 0.5f
        window?.attributes = lp
    }


    override fun show() {
        super.show()
        binding.ivClose.setOnClickListener { dismiss() }
    }

    fun setTpwdValue(v: String){
        binding.tvTpwd.text = v
    }

    fun getTpwdValue():String{
        return binding.tvTpwd.text.toString()
    }
    fun setBtnText(v: String){
        binding.btnOpenTaobao.text = v
    }

    fun onOpenTaobao(listener: View.OnClickListener){
        binding.btnOpenTaobao.setOnClickListener(listener)
    }
}