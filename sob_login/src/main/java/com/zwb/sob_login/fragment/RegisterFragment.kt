package com.zwb.sob_login.fragment

import androidx.fragment.app.viewModels
import com.tencent.smtt.utils.Md5Utils
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.EventBusUtils
import com.zwb.sob_login.LoginApi
import com.zwb.sob_login.LoginViewModel
import com.zwb.sob_login.activity.LoginActivity
import com.zwb.sob_login.bean.RegisterBean
import com.zwb.sob_login.bean.SendSmsBean
import com.zwb.sob_login.databinding.LoginFragmentRegisterBinding
import com.zwb.sob_login.view.CodeEditView

class RegisterFragment : BaseFragment<LoginFragmentRegisterBinding, LoginViewModel>() {
    override val mViewModel by viewModels<LoginViewModel>()

    override fun LoginFragmentRegisterBinding.initView() {
        this.btnRegister.setOnClickListener {
            register()
        }
        this.tvLogin.setOnClickListener {
            EventBusUtils.postEvent(LoginActivity.PageType.SWITCH_LOGIN)
        }
        this.tvForget.setOnClickListener {
            EventBusUtils.postEvent(LoginActivity.PageType.SWITCH_FORGET)
        }

        this.editPhoneCode.setPhoneCodeListener(object : CodeEditView.PhoneCodeListener {

            override fun sendMessage() {
                mViewModel.registerSms(
                    SendSmsBean(mBinding.editPhone.getValue(), mBinding.editTuringCode.getValue()),
                    LoginApi.REGISTER_SMS_URL
                ).observe(viewLifecycleOwner,{
                    if(it.success) {
                        mBinding.editPhoneCode.timerStart()
                    } else {
                        mBinding.editTuringCode.initTuringCode()
                        toast(it.message)
                    }
                })
            }

            override fun isPreContented(): Boolean {
                return checkTuringAndPhone()
            }

        })

    }

    override fun initObserve() {
    }

    override fun initRequestData() {

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            mBinding.editTuringCode.initTuringCode()
        } else {
            mBinding.editPhoneCode.timerCancel()
        }
    }

    private fun checkTuringAndPhone(): Boolean {
        if (mBinding.editTuringCode.getValue().isEmpty()) {
            toast("请输入图灵验证码")
            return false
        }
        if (mBinding.editPhone.getValue().length != 11) {
            toast("请输入正确的手机号")
            return false
        }
        return true
    }


    private fun checkData(): Boolean {
        if (!checkTuringAndPhone()) return false
        if (mBinding.editPhoneCode.getValue().isEmpty()) {
            toast("请输入短信验证码")
            return false
        }
        if (mBinding.editNickname.getValue().isEmpty()) {
            toast("请输入昵称")
            return false
        }
        if (mBinding.editPassword.getValue().isEmpty()) {
            toast("请输入密码")
            return false
        }
        return true
    }

    private fun register() {
        if (!checkData()) return
        val registerBean = RegisterBean(
            mBinding.editPhone.getValue(),
            Md5Utils.getMD5(mBinding.editPassword.getValue()),
            mBinding.editNickname.getValue()
        )
        showLoading()
        mViewModel.register(mBinding.editPhoneCode.getValue(), registerBean, LoginApi.REGISTER_URL)
            .observe(viewLifecycleOwner, {
                mBinding.editTuringCode.initTuringCode()
                dismissLoading()
                if (it.success) {
                    toast(it.message)
                } else {
                    toast(it.message)
                }
            })
    }

}