package com.zwb.sob_ucenter.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.sob_ucenter.R
import com.zwb.sob_ucenter.bean.MsgSystemBean

class MsgSystemAdapter(list: MutableList<MsgSystemBean?>) :
    BaseQuickAdapter<MsgSystemBean, BaseViewHolder>(R.layout.ucenter_adapter_sysmsg_list, list) {

    override fun convert(helper: BaseViewHolder, item: MsgSystemBean?) {
        item?.let {
            helper.setText(R.id.tv_title,it.title)
            helper.setText(R.id.tv_createTime,it.publishTime)
            CommonViewUtils.setHtml(helper.getView(R.id.tv_content),it.content)
        }
    }
}