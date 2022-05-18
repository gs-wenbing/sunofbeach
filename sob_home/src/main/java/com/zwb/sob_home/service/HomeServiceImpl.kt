package com.zwb.sob_home.service

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.home.IHomeService
import com.zwb.sob_home.fragment.HomeMainFragment

@Route(path = RoutePath.Home.SERVICE_HOME)
class HomeServiceImpl:IHomeService {

    override fun getFragment(): Fragment {
        return HomeMainFragment()
    }

    override fun init(context: Context?) {
    }
}