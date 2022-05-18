package com.zwb.sob_login.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.CountDownTimer
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.utils.UIUtils
import com.zwb.sob_login.R
import com.zwb.sob_login.databinding.LoginCodeLayoutBinding


class CodeEditView : LinearLayout, View.OnFocusChangeListener {

    private var codeUrl = "https://api.sunofbeaches.com/uc/ut/captcha?code="

    private lateinit var binding: LoginCodeLayoutBinding

    private var leftIcon = R.mipmap.ic_phone
    private var hint: String? = null

    private var isPhoneCode: Boolean = false
    private var timer: CountDownTimer? = null
    private var phoneCodeListener: PhoneCodeListener? = null

    interface PhoneCodeListener {
        fun sendMessage()
        fun isPreContented(): Boolean
    }

    fun setPhoneCodeListener(listener: PhoneCodeListener?) {
        this.phoneCodeListener = listener
    }

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun init(context: Context, attrs: AttributeSet?) {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.CodeEditView)
        leftIcon = attr.getResourceId(R.styleable.CodeEditView_codeLeftIcon, leftIcon)
        hint = attr.getString(R.styleable.CodeEditView_codeHint)
        isPhoneCode = attr.getBoolean(R.styleable.CodeEditView_isPhoneCode, false)
        attr.recycle()
        binding = LoginCodeLayoutBinding.inflate(LayoutInflater.from(context), this, true)

        if (isPhoneCode) {
            binding.btnMsgCode.visible()
            binding.ivTuring.gone()
            initTimer()
        } else {
            binding.btnMsgCode.gone()
            binding.ivTuring.visible()
            initTuringCode()
        }
        binding.editInput.hint = hint
        // 设置drawableLeft
        val drawable = resources.getDrawable(leftIcon, null)
        setDrawableLeft(drawable)

        initListener()
    }

    fun getValue(): String{
        return if(binding.editInput.text != null) binding.editInput.text.toString() else ""
    }

    private fun setDrawableLeft(drawable: Drawable) {
        // 这一步必须要做，否则不会显示
        drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
        binding.editInput.setCompoundDrawables(drawable, null, null, null)
    }

    private fun switchViewStyle(bgResId: Int, leftIconColor: Int) {
        binding.inputLayout.setBackgroundResource(bgResId)
        setDrawableLeft(
            UIUtils.tintDrawable(
                binding.editInput.compoundDrawables[0],
                ColorStateList.valueOf(ContextCompat.getColor(context, leftIconColor))
            )
        )
    }

    private fun initTimer() {
        timer = object : CountDownTimer(1000 * 60, 1000) {

            @SuppressLint("SetTextI18n")
            @RequiresApi(Build.VERSION_CODES.M)
            override fun onTick(sin: Long) {
                binding.btnMsgCode.text = "${sin / 1000}秒后重发"
                binding.btnMsgCode.isEnabled = false
                binding.btnMsgCode.setTextColor(ContextCompat.getColor(context,R.color.grey_light))
                if ((sin / 1000) == 0L) {
                    resetText()
                }
            }

            override fun onFinish() {

            }
        }
    }

    private fun resetText(){
        binding.btnMsgCode.isEnabled = true
        binding.btnMsgCode.text = "获取验证码"
        binding.btnMsgCode.setTextColor(ContextCompat.getColor(context,R.color.colorPrimary))
    }

    fun initTuringCode() {
        val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        Glide.with(context).load(codeUrl + (0..100).random()).apply(options).into(binding.ivTuring)
    }

    fun timerCancel() {
        timer?.cancel()
    }

    fun timerStart() {
        timer?.start()
        resetText()
    }

    private fun initListener() {
        binding.editInput.onFocusChangeListener = this
        binding.btnMsgCode.setOnClickListener {
            phoneCodeListener?.let {
                if (it.isPreContented()) {
                    it.sendMessage()
                }
            }
        }
        binding.ivTuring.setOnClickListener {
            initTuringCode()
        }
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        var bgResId = R.drawable.shape_login_input
        var leftIconColor = R.color.grey_light
        if (hasFocus) {
            bgResId = R.drawable.shape_login_input_focus
            leftIconColor = R.color.colorPrimary
            binding.inputLayout.setBackgroundResource(R.drawable.shape_login_input_focus)
        }
        switchViewStyle(bgResId, leftIconColor)
    }


}