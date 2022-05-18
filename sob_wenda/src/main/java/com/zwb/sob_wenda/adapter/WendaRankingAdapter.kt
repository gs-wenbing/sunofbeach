package com.zwb.sob_wenda.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_common.view.AvatarDecorView
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.sob_wenda.R
import com.zwb.sob_wenda.bean.WendaRankingBean

class WendaRankingAdapter(data: List<WendaRankingBean>)
    : BaseQuickAdapter<WendaRankingBean, BaseViewHolder>(R.layout.wenda_adapter_ranking,data) {

    override fun convert(helper: BaseViewHolder, item: WendaRankingBean?) {
        item?.let {
            val ivAvatar = helper.getView<AvatarDecorView>(R.id.iv_avatar)
            ivAvatar.loadAvatar(it.vip,it.avatar)

            helper.setText(R.id.tv_nickname,it.nickname)
            helper.setText(R.id.tv_count,"${it.count} 个回答")
            CommonViewUtils.setFollowState(helper.getView(R.id.btn_follow),0)
            helper.addOnClickListener(R.id.btn_follow)
        }
    }
}