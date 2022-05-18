package com.zwb.sob.activity

import android.os.Handler
import android.util.Log
import androidx.activity.viewModels
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_common.constant.SpKey
import com.zwb.sob.MainViewModel
import com.zwb.sob.databinding.ActivitySplashBinding

class SplashActivity:BaseActivity<ActivitySplashBinding,MainViewModel>() {
    override val mViewModel by viewModels<MainViewModel>()

    override fun ActivitySplashBinding.initView() {
        Handler().postDelayed({
            // 第一次启动，跳转至引导页
            if(SpUtils.getBoolean(SpKey.IS_FIRST_LAUNCHER,true) == null || SpUtils.getBoolean(SpKey.IS_FIRST_LAUNCHER,true)!! ){
                GuideActivity.launch(this@SplashActivity)
            }else{
                MainActivity.launch(this@SplashActivity)
            }
            finish()
        },500)
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }
}