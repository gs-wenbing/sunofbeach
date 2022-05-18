package com.zwb.lib_common.service.ucenter.wrap

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_common.bean.CollectionBean
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.ucenter.IUcenterService

class UcenterServiceWrap private constructor() {

    @Autowired(name = RoutePath.Ucenter.SERVICE_UCENTER)
    lateinit var service: IUcenterService

    init {
        ARouter.getInstance().inject(this)
    }

    fun getFragment(): Fragment {
        return service.getFragment()
    }

    fun launchDetail(userId:String?){
        ARouter.getInstance()
            .build(RoutePath.Ucenter.PAGE_UCENTER)
            .withString(RoutePath.Ucenter.PARAMS_USER_ID, userId)
            .navigation()
    }

    fun launchMassage(){
        ARouter.getInstance()
            .build(RoutePath.Ucenter.PAGE_MESSAGE)
            .navigation()
    }

    fun launchUcenterList(pageType: Int,userId: String){
        ARouter.getInstance()
            .build(RoutePath.Ucenter.PAGE_UCENTER_LIST)
            .withInt(Constants.Ucenter.PAGE_TYPE, pageType)
            .withString("userId", userId)
            .navigation()
    }
    fun launchFavoriteList(collection: CollectionBean){
        ARouter.getInstance()
            .build(RoutePath.Ucenter.PAGE_FAVORITE_LIST)
            .withParcelable("collection", collection)
            .navigation()
    }

    companion object {
        val instance = Singleton.holder
        object Singleton {
            val holder = UcenterServiceWrap()
        }
    }
}