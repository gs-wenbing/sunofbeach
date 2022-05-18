package com.zwb.sob.activity

import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.sob.GuideBean
import com.zwb.sob.R

class GuideAdapter(list: MutableList<GuideBean>) :
    BaseQuickAdapter<GuideBean, BaseViewHolder>(R.layout.adapter_guide, list) {

    override fun convert(helper: BaseViewHolder, item: GuideBean) {
        helper.setText(R.id.tv_title,item.title)
        helper.setText(R.id.tv_desc,item.desc)
        helper.setImageResource(R.id.iv_guide,item.resId)
        val tvHello = helper.getView<TextView>(R.id.tv_hello)
        if(data.indexOf(item) == 2){
            tvHello.visible()
        }else{
            tvHello.gone()
        }
        helper.addOnClickListener(R.id.tv_hello)
    }
}