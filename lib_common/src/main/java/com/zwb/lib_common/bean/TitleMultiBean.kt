package com.zwb.lib_common.bean

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_common.constant.Constants

data class TitleMultiBean(var title: String): MultiItemEntity{
    override fun getItemType(): Int {
        return Constants.MultiItemType.TYPE_TITLE
    }

}