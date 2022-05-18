package com.zwb.sob_ucenter.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.utils.EventBusUtils
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_common.constant.SpKey
import com.zwb.lib_common.event.StringEvent
import com.zwb.sob_ucenter.UcenterApi
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.databinding.UcenterActivitySettingBinding

class SettingActivity : BaseActivity<UcenterActivitySettingBinding,UcenterViewModel>() {
    override val mViewModel by viewModels<UcenterViewModel>()

    override fun UcenterActivitySettingBinding.initView() {
        mBinding.btnLogout.setOnClickListener {
//            mViewModel.logout(UcenterApi.LOGOUT_URL).observe(this@SettingActivity,{
//                if(it.success){
//
//                }else{
//                    toast(it.message)
//                }
//
//            })
            SpUtils.putBoolean(SpKey.IS_LOGIN,false)
            SpUtils.putString(RetrofitFactory.SOB_TOKEN, "")
            SpUtils.putString(RetrofitFactory.L_C_I, "")
            EventBusUtils.postEvent(StringEvent(StringEvent.Event.SWITCH_HOME))
            finish()
        }
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }


    companion object {
        fun launch(activity: FragmentActivity) =
            activity.apply {
                val intent = Intent(this, SettingActivity::class.java)
                startActivity(intent)
            }
    }
}