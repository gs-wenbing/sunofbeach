package com.zwb.lib_common.service.wenda.wrap

import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.wenda.IWendaService

class WendaServiceWrap private constructor() {

    @Autowired(name = RoutePath.Wenda.SERVICE_WENDA)
    lateinit var service: IWendaService

    init {
        ARouter.getInstance().inject(this)
    }

    fun getFragment(): Fragment {
        return service.getFragment()
    }

    fun launchDetail(wendaId: String) {
        ARouter.getInstance()
            .build(RoutePath.Wenda.PAGE_DETAIL)
            .withString(RoutePath.Wenda.PARAMS_WENDA_ID, wendaId)
            .navigation()
    }

    fun launchAnswerDetail(wenda: Parcelable?, answer: Parcelable?) {
        ARouter.getInstance()
            .build(RoutePath.Wenda.PAGE_ANSWER_DETAIL)
            .withParcelable(RoutePath.Wenda.PARAMS_ANSWER, answer)
            .withParcelable(RoutePath.Wenda.PARAMS_WENDA_CONTENT, wenda)
            .navigation()
    }

    companion object {
        val instance = Singleton.holder

        object Singleton {
            val holder = WendaServiceWrap()
        }
    }
}