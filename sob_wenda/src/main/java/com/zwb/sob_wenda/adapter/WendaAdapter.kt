package com.zwb.sob_wenda.adapter

import androidx.core.content.ContextCompat
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_common.view.AvatarDecorView
import com.zwb.sob_wenda.R
import com.zwb.sob_wenda.bean.WendaBean

class WendaAdapter(data: List<WendaBean>)
    : BaseQuickAdapter<WendaBean, BaseViewHolder>(R.layout.wenda_adapter,data) {
    override fun convert(helper: BaseViewHolder, item: WendaBean?) {
        item?.let {
            helper.setText(R.id.tv_viewCount,"${it.viewCount}浏览")
            helper.setText(R.id.tv_title,it.title)
            helper.setText(R.id.tv_nickname,it.nickname)
            helper.setText(R.id.tv_sob,it.sob.toString())
            helper.setText(R.id.tv_labels,it.labels.toString())

            val tvAnswerCount = helper.getView<SuperTextView>(R.id.tv_answerCount)
            tvAnswerCount.setCenterTopString(it.answerCount.toString())
            if(it.isResolve=="1"){
                tvAnswerCount.setSBackground(ContextCompat.getDrawable(mContext,R.drawable.green_solid_btn_normal))
                tvAnswerCount.setCenterString("√")
                tvAnswerCount.setCenterTextColor(ContextCompat.getColor(mContext,R.color.white))
                tvAnswerCount.setCenterTopTextColor(ContextCompat.getColor(mContext,R.color.white))
            }else{
                tvAnswerCount.setSBackground(ContextCompat.getDrawable(mContext,R.drawable.green_hollow_btn_normal))
                tvAnswerCount.setCenterString("回答")
                tvAnswerCount.setCenterTextColor(ContextCompat.getColor(mContext,R.color.colorSuccess))
                tvAnswerCount.setCenterTopTextColor(ContextCompat.getColor(mContext,R.color.colorSuccess))
            }

            val ivAvatar = helper.getView<AvatarDecorView>(R.id.iv_avatar)
            ivAvatar.loadAvatar(it.isVip=="1",it.avatar)

            helper.addOnClickListener(R.id.iv_avatar,R.id.tv_nickname)
        }
    }
}