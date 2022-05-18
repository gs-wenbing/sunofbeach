package com.zwb.sob_ucenter.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.sob_ucenter.R
import com.zwb.sob_ucenter.bean.FavoriteBean

class FavoriteAdapter(list:MutableList<FavoriteBean?>):
    BaseQuickAdapter<FavoriteBean, BaseViewHolder>(R.layout.ucenter_adapter_favorite,list) {

    override fun convert(helper: BaseViewHolder, item: FavoriteBean?) {
        item?.let { favorite ->
            helper.setText(R.id.tv_content,favorite.title)
            helper.setText(R.id.tv_addTime,favorite.addTime)
            when(favorite.type){
                "0" -> helper.setText(R.id.tv_type, "文章")
                else->{}
            }
        }
    }
}