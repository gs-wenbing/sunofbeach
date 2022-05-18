package com.zwb.lib_common.service.shop

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider

interface IShopService: IProvider {
    fun getFragment(): Fragment
}