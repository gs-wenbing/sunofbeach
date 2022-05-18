package com.zwb.sob_shop.serivce

import android.content.Context
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.shop.IShopService
import com.zwb.sob_shop.fragment.ShopMainFragment

@Route(path = RoutePath.Shop.SERVICE_SHOP)
class ShopServiceImpl:IShopService {

    override fun getFragment(): Fragment {
        return ShopMainFragment()
    }

    override fun init(context: Context?) {

    }
}