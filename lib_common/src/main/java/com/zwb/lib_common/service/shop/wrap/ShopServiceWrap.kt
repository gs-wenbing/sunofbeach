package com.zwb.lib_common.service.shop.wrap

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.shop.IShopService

class ShopServiceWrap private constructor() {

    @Autowired(name = RoutePath.Shop.SERVICE_SHOP)
    lateinit var service: IShopService

    init {
        ARouter.getInstance().inject(this)
    }

    fun getFragment(): Fragment {
        return service.getFragment()
    }

    companion object {
        val instance = Singleton.holder
        object Singleton {
            val holder = ShopServiceWrap()
        }
    }
}