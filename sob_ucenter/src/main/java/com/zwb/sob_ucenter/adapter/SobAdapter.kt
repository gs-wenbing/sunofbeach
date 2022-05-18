package com.zwb.sob_ucenter.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_base.utils.DateUtils
import com.zwb.sob_ucenter.R
import com.zwb.sob_ucenter.bean.SobBean

class SobAdapter(list: MutableList<SobBean?>) :
    BaseQuickAdapter<SobBean, BaseViewHolder>(R.layout.ucenter_adapter_sob, list) {

    override fun convert(helper: BaseViewHolder, item: SobBean?) {
        item?.let { sob ->
            helper.setText(R.id.tv_content,sob.content)
            //时间转换 暂时现用DateUtils.timeFormat
            helper.setText(R.id.tv_createTime, DateUtils.timeFormat(sob.createTime))
            helper.setText(R.id.tv_sob,"+ ${sob.sob}")
        }
    }
}