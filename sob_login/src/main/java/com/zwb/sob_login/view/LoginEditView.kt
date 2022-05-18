package com.zwb.sob_login.view

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.utils.UIUtils
import com.zwb.sob_login.R
import com.zwb.sob_login.databinding.LoginInputLayoutBinding


class LoginEditView : LinearLayout, TextWatcher, View.OnFocusChangeListener {

    private lateinit var binding: LoginInputLayoutBinding

    private var leftIcon = R.mipmap.ic_phone
    private var hint: String? = null
    private var isPassword: Boolean = false
    // 只有密码框才用到
    // 不可见密码： InputType.TYPE_TEXT_VARIATION_PASSWORD
    // 可见密码：   InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD or InputType.TYPE_CLASS_TEXT
    private var isPsdVisible: Boolean = false

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init(context, attrs)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun init(context: Context, attrs: AttributeSet?) {
        val attr = context.obtainStyledAttributes(attrs, R.styleable.LoginEditView)
        leftIcon = attr.getResourceId(R.styleable.LoginEditView_inputLeftIcon, leftIcon)
        hint = attr.getString(R.styleable.LoginEditView_inputHint)
        isPassword = attr.getBoolean(R.styleable.LoginEditView_isPassword, false)
        attr.recycle()
        binding = LoginInputLayoutBinding.inflate(LayoutInflater.from(context), this, true)
        binding.ivClear.gone()
        if (isPassword) {
            binding.ivEye.visible()
            binding.editInput.inputType =
                InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            isPsdVisible = false
        } else {
            binding.editInput.inputType = InputType.TYPE_CLASS_TEXT
            binding.ivEye.gone()
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

    private fun initListener(){
        binding.editInput.addTextChangedListener(this)
        binding.editInput.onFocusChangeListener = this
        binding.ivEye.setOnClickListener {
            if (isPsdVisible) {
                // 密文
                isPsdVisible = false
                binding.editInput.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
                binding.ivEye.setImageResource(R.mipmap.ic_visible)
            }else {
                // 明文
                isPsdVisible = true
                binding.editInput.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
                binding.ivEye.setImageResource(R.mipmap.ic_invisible)
            }
            binding.editInput.setSelection(binding.editInput.text.length)
        }
        binding.ivClear.setOnClickListener {
            binding.editInput.setText("")
            binding.ivClear.gone()
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


    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

    }

    override fun afterTextChanged(s: Editable?) {
        s?.let {
            if(it.toString().isNotEmpty() && binding.editInput.isFocused){
                binding.ivClear.visible()
            }else{
                binding.ivClear.gone()
            }
        }
    }

}