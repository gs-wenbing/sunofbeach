package com.zwb.sob_ucenter.serivce

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.ucenter.IUcenterService
import com.zwb.sob_ucenter.fragment.UcenterMainFragment

@Route(path = RoutePath.Ucenter.SERVICE_UCENTER)
class UcenterServiceImpl:IUcenterService {
    override fun getFragment(): Fragment {
        return UcenterMainFragment()
    }

    override fun init(context: Context?) {
    }
}