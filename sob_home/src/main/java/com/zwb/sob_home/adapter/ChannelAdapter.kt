package com.zwb.sob_home.adapter

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.sob_home.R
import com.zwb.sob_home.bean.CategoryBean

class ChannelAdapter(private val type:Int, list:MutableList<CategoryBean?>):
    BaseQuickAdapter<CategoryBean, BaseViewHolder>(R.layout.home_adapter_channel,list) {

    override fun convert(helper: BaseViewHolder, item: CategoryBean?) {
        item?.let {
            if(type==1){
                helper.setBackgroundRes(R.id.tv_name,R.drawable.shape_grey_background)
                if(item.id=="1"){
                    helper.getView<View>(R.id.iv_opr).gone()
                }else{
                    helper.getView<View>(R.id.iv_opr).visible()
                }
                helper.setImageResource(R.id.iv_opr,R.mipmap.ic_reduce_fill)
            }else{
                helper.setBackgroundRes(R.id.tv_name,R.drawable.shape_white_background)
                helper.setImageResource(R.id.iv_opr,R.mipmap.ic_plus_fill)
            }
            helper.setText(R.id.tv_name,it.categoryName)
        }
    }
}