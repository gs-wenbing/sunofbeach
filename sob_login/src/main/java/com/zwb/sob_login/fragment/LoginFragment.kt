package com.zwb.sob_login.fragment

import androidx.fragment.app.viewModels
import com.tencent.smtt.utils.Md5Utils
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.EventBusUtils
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_common.constant.SpKey
import com.zwb.sob_login.LoginApi
import com.zwb.sob_login.LoginViewModel
import com.zwb.sob_login.activity.LoginActivity
import com.zwb.sob_login.bean.LoginInBean
import com.zwb.sob_login.databinding.LoginFragmentBinding


class LoginFragment : BaseFragment<LoginFragmentBinding, LoginViewModel>() {

    override val mViewModel by viewModels<LoginViewModel>()

    override fun LoginFragmentBinding.initView() {
        this.btnLogin.setOnClickListener {
            login()
        }
        this.tvRegister.setOnClickListener {
            EventBusUtils.postEvent(LoginActivity.PageType.SWITCH_REGISTER)
        }
        this.tvForget.setOnClickListener {
            EventBusUtils.postEvent(LoginActivity.PageType.SWITCH_FORGET)
        }
    }

    override fun initObserve() {
    }

    override fun initRequestData() {

    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if(!hidden){
            mBinding.editTuringCode.initTuringCode()
        }
    }

    private fun login() {
        if(mBinding.editPhone.getValue().length!=11){
            toast("请输入正确的手机号")
            return
        }
        if(mBinding.editPassword.getValue().isEmpty()){
            toast("请输入密码")
            return
        }
        if(mBinding.editTuringCode.getValue().isEmpty()){
            toast("请输入验证码")
            return
        }
        showLoading()
        val loginInBean = LoginInBean(mBinding.editPhone.getValue(), Md5Utils.getMD5(mBinding.editPassword.getValue()))
        mViewModel.login(mBinding.editTuringCode.getValue(), loginInBean, LoginApi.LOGIN_URL)
            .observe(viewLifecycleOwner, {
                mBinding.editTuringCode.initTuringCode()
                dismissLoading()
                if(it.success){
                    SpUtils.putBoolean(SpKey.IS_LOGIN, true)
                    EventBusUtils.postEvent(LoginActivity.PageType.LOGIN_SUCCESS)
                }else{
                    SpUtils.putBoolean(SpKey.IS_LOGIN, false)
                    toast(it.message)
                }
            })
    }



}