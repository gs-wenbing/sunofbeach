package com.zwb.lib_common.service.ucenter

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

interface IUcenterService: IProvider {
    fun getFragment(): Fragment
}