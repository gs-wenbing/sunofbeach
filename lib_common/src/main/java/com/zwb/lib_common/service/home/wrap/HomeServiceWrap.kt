package com.zwb.lib_common.service.home.wrap

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.home.IHomeService

class HomeServiceWrap private constructor() {

    @Autowired(name = RoutePath.Home.SERVICE_HOME)
    lateinit var service: IHomeService

    init {
        ARouter.getInstance().inject(this)
    }

    fun getFragment(): Fragment {
        return service.getFragment()
    }

    fun launchDetail(id: String, title: String) {
        ARouter.getInstance()
            .build(RoutePath.Home.PAGE_ARTICLE)
            .withString("articleId", id)
            .withString("articleTitle", title)
            .navigation()
    }

    companion object {
        val instance = Singleton.holder

        object Singleton {
            val holder = HomeServiceWrap()
        }
    }
}