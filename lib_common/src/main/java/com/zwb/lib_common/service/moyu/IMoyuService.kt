package com.zwb.lib_common.service.moyu

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

interface IMoyuService: IProvider {
    fun getFragment(): Fragment
}