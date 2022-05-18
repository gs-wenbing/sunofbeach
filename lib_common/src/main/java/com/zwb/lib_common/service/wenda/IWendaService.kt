package com.zwb.lib_common.service.wenda

import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.template.IProvider


interface IWendaService: IProvider {
    fun getFragment():Fragment
}