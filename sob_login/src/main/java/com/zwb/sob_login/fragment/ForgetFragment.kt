package com.zwb.sob_login.fragment

import androidx.fragment.app.viewModels
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.EventBusUtils
import com.zwb.sob_login.LoginViewModel
import com.zwb.sob_login.activity.LoginActivity
import com.zwb.sob_login.databinding.LoginFragmentForgetBinding
import com.zwb.sob_login.view.CodeEditView

class ForgetFragment: BaseFragment<LoginFragmentForgetBinding, LoginViewModel>() {
    override val mViewModel by viewModels<LoginViewModel>()

    override fun LoginFragmentForgetBinding.initView() {
        this.tvLogin.setOnClickListener {
            EventBusUtils.postEvent(LoginActivity.PageType.SWITCH_LOGIN)
        }
        this.tvRegister.setOnClickListener {
            EventBusUtils.postEvent(LoginActivity.PageType.SWITCH_REGISTER)
        }

        this.editPhoneCode.setPhoneCodeListener(object : CodeEditView.PhoneCodeListener {

            override fun sendMessage() {
//                mViewModel.forgetSms(
//                    SendSmsBean(mBinding.editPhone.getValue(), mBinding.editTuringCode.getValue()),
//                    LoginApi.FORGET_SMS_URL
//                ).observe(viewLifecycleOwner,{
//                    if(it.success) {
//                        mBinding.editPhoneCode.timerStart()
//                    } else {
//                        mBinding.editTuringCode.initTuringCode()
//                        toast(it.message)
//                    }
//                })
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
}