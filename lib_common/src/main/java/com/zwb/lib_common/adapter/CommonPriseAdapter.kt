package com.zwb.lib_common.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_common.R
import com.zwb.lib_common.bean.PriseSobBean

class CommonPriseAdapter()
    : BaseQuickAdapter<PriseSobBean, BaseViewHolder>(R.layout.common_adapter_prise) {

    init {
        for (index in 1..6) {
            when (index) {
                in (1..3) -> data.add(PriseSobBean("${index*2} 币",index*2))
                4 ->  data.add(PriseSobBean("10 币",10))
                5 -> data.add(PriseSobBean("15 币",15))
                6 -> data.add(PriseSobBean("20 币",20,true))
            }
        }
    }
    override fun convert(helper: BaseViewHolder, item: PriseSobBean?) {
        item?.let {
            helper.setText(R.id.tv_sob_value,it.label)
            helper.itemView.setBackgroundResource(if(it.isChecked) R.drawable.blue_hollow_btn_normal else R.drawable.shape_grey_background)
        }
    }
}