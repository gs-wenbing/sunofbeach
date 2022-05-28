package com.zwb.sob.activity

import android.os.Handler
import androidx.activity.viewModels
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.SpKey
import com.zwb.sob.MainViewModel
import com.zwb.sob.databinding.ActivitySplashBinding

class SplashActivity:BaseActivity<ActivitySplashBinding, MainViewModel>() {
    override val mViewModel by viewModels<MainViewModel>()

    private var h = Handler()
    private lateinit var runnable: Runnable

    override fun ActivitySplashBinding.initView() {
        // 第一次启动，跳转至引导页
        if(SpUtils.getBoolean(SpKey.IS_FIRST_LAUNCHER, true) == null
            || SpUtils.getBoolean(SpKey.IS_FIRST_LAUNCHER,true)!! ){
            runnable = Runnable {
                GuideActivity.launch(this@SplashActivity)
                finish()
            }
            h.postDelayed(runnable, 500)
        }else{
            // 刷新token
            mViewModel.checkToken(Constants.URL.CHECK_TOKEN_URL).observe(this@SplashActivity, {
                toMain()
            })
            runnable = Runnable {
                toMain()
            }
            h.postDelayed(runnable, 500)
        }
    }

    private fun toMain(){
        if(!this.isDestroyed && !this.isFinishing){
            h.removeCallbacks(runnable)
            MainActivity.launch(this)
            finish()
        }
    }


    override fun initObserve() {
    }

    override fun initRequestData() {

    }
}