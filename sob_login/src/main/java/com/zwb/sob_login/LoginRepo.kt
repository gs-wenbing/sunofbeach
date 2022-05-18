package com.zwb.sob_login

import androidx.lifecycle.MutableLiveData
import com.zwb.lib_base.ktx.dataConvert
import com.zwb.lib_base.mvvm.m.BaseRepository
import com.zwb.lib_base.net.BaseResponse
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.net.State
import com.zwb.lib_common.bean.TokenBean
import com.zwb.sob_login.bean.LoginInBean
import com.zwb.sob_login.bean.ModifyPwdBean
import com.zwb.sob_login.bean.RegisterBean
import com.zwb.sob_login.bean.SendSmsBean

class LoginRepo(private val loadState: MutableLiveData<State>) : BaseRepository() {
    private val apiService by lazy {
        RetrofitFactory.instance.getService(LoginApi::class.java, LoginApi.BASE_URL)
    }

    suspend fun login(captcha: String, query: LoginInBean): BaseResponse<String?> {
        return apiService.login(captcha, query)
    }

    suspend fun checkToken(key: String): TokenBean? {
        return apiService.checkToken().dataConvert(loadState, key)
    }

    suspend fun registerSms(query: SendSmsBean): BaseResponse<String?> {
        return apiService.registerSms(query)
    }

    suspend fun checkSmsCode(phoneNumber: String, smsCode: String): BaseResponse<String?> {
        return apiService.checkSmsCode(phoneNumber, smsCode)
    }

    suspend fun register(smsCode: String, query: RegisterBean): BaseResponse<String?> {
        return apiService.register(smsCode, query)
    }

    suspend fun forgetSms(query: SendSmsBean): BaseResponse<String?> {
        return apiService.forgetSms(query)
    }

    suspend fun forget(smsCode: String, query: LoginInBean): BaseResponse<String?> {
        return apiService.forget(smsCode, query)
    }

    suspend fun modifyPsd(query: ModifyPwdBean): BaseResponse<String?> {
        return apiService.modifyPsd(query)
    }
}