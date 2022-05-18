package com.zwb.sob_login

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.initiateRequest
import com.zwb.lib_base.mvvm.vm.BaseViewModel
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_common.constant.SpKey
import com.zwb.sob_login.bean.LoginInBean
import com.zwb.sob_login.bean.RegisterBean
import com.zwb.sob_login.bean.SendSmsBean

class LoginViewModel : BaseViewModel() {
    private val repository by lazy {
        LoginRepo(loadState)
    }

    fun login(captcha: String, query: LoginInBean, key: String): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            val res = repository.login(captcha, query)
            if(res.success){
                val token = repository.checkToken(key)
                token?.let {
                    SpUtils.putString(SpKey.USER_ID, token.id)
                    SpUtils.putString(SpKey.USER_AVATAR, if(TextUtils.isEmpty(token.avatar))"" else token.avatar!! )
                    SpUtils.putString(SpKey.USER_NICKNAME, if(TextUtils.isEmpty(token.nickname))"" else token.nickname!! )
                }
            }
            response.value = res
        }, loadState, key)
        return response
    }

    fun registerSms(query: SendSmsBean, key: String): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            response.value = repository.registerSms(query)
        }, loadState, key)
        return response
    }

    fun register(smsCode: String, query: RegisterBean, key: String): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            val res = repository.checkSmsCode(query.phoneNum, smsCode)
            if (res.success) {
                response.value = repository.register(smsCode, query)
            } else {
                response.value = res
            }
        }, loadState, key)
        return response
    }

    fun forgetSms(query: SendSmsBean, key: String): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            response.value = repository.forgetSms(query)
        }, loadState, key)
        return response
    }

    fun forget(smsCode: String, query: LoginInBean, key: String): MutableLiveData<BaseResponse<String?>> {
        val response: MutableLiveData<BaseResponse<String?>> = MutableLiveData()
        initiateRequest({
            val res = repository.checkSmsCode(query.phoneNum, smsCode)
            if (res.success) {
                response.value = repository.forget(smsCode, query)
            } else {
                response.value = res
            }
        }, loadState, key)
        return response
    }
}