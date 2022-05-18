package com.zwb.sob_wenda.serivce

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.wenda.IWendaService
import com.zwb.sob_wenda.fragment.WendaMainFragment

@Route(path = RoutePath.Wenda.SERVICE_WENDA)
class WendaServiceImpl:IWendaService {

    override fun getFragment(): Fragment {
        return WendaMainFragment()
    }


    override fun init(context: Context?) {

    }
}