package com.zwb.sob.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_common.constant.SpKey
import com.zwb.sob.GuideBean
import com.zwb.sob.MainViewModel
import com.zwb.sob.R
import com.zwb.sob.databinding.ActivityGuideBinding

class GuideActivity : BaseActivity<ActivityGuideBinding, MainViewModel>() {
    override val mViewModel by viewModels<MainViewModel>()

    override fun ActivityGuideBinding.initView() {
        val list = mutableListOf<GuideBean>()
        list.add(GuideBean(R.mipmap.guide1, "我们的使命", "让学习编程变得更加简单"))
        list.add(GuideBean(R.mipmap.guide2, "我们的愿景", "让热爱编程的年轻人成为优秀的工程师"))
        list.add(GuideBean(R.mipmap.guide3, "一起上班摸鱼", "赚钱的同时，顺便交朋友"))
        val adapter = GuideAdapter(list)
        vp2Guide.offscreenPageLimit = 3
        vp2Guide.adapter = adapter
        guideIndicator.setViewPager2(vp2Guide, 3)

        adapter.setOnItemChildClickListener { _, view, _ ->
            when (view.id) {
                R.id.tv_hello -> {
                    MainActivity.launch(this@GuideActivity)
                    SpUtils.put(SpKey.IS_FIRST_LAUNCHER, false)
                    finish()
                }
            }
        }
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }

    companion object {
        fun launch(activity: FragmentActivity) =
            activity.apply {
                val intent = Intent(this, GuideActivity::class.java)
                startActivity(intent)
            }
    }
}