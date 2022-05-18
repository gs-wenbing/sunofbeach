package com.zwb.lib_common.service.moyu.wrap

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_common.bean.MoyuItemBean
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.moyu.IMoyuService

class MoyuServiceWrap private constructor() {

    @Autowired(name = RoutePath.Moyu.SERVICE_MOYU)
    lateinit var service: IMoyuService

    init {
        ARouter.getInstance().inject(this)
    }

    fun getFragment(): Fragment {
        return service.getFragment()
    }


    fun launchDetail(moyu: MoyuItemBean) {
        ARouter.getInstance()
            .build(RoutePath.Moyu.PAGE_DETAIL)
            .withParcelable(RoutePath.Moyu.PARAMS_MOYU, moyu)
            .navigation()
    }

    companion object {
        val instance = Singleton.holder
        object Singleton {
            val holder = MoyuServiceWrap()
        }
    }
}