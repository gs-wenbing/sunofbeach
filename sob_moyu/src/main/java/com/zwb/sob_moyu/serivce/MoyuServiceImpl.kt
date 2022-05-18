package com.zwb.sob_moyu.serivce

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.moyu.IMoyuService
import com.zwb.sob_moyu.fragment.MoyuMainFragment

@Route(path = RoutePath.Moyu.SERVICE_MOYU)
class MoyuServiceImpl:IMoyuService {

    override fun getFragment(): Fragment {
        return MoyuMainFragment()
    }

    override fun init(context: Context?) {
    }
}