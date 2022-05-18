package com.zwb.sob_ucenter.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_common.view.AvatarDecorView
import com.zwb.sob_ucenter.R
import com.zwb.sob_ucenter.bean.RankingSobBean

class RankingSobAdapter(list: MutableList<RankingSobBean?>) :
    BaseQuickAdapter<RankingSobBean, BaseViewHolder>(R.layout.ucenter_adapter_ranking_sob, list) {

    override fun convert(helper: BaseViewHolder, item: RankingSobBean?) {
        item?.let { sob ->
            val ivAvatar = helper.getView<AvatarDecorView>(R.id.iv_avatar)
            ivAvatar.loadAvatar(sob.vip,sob.avatar)

            helper.setText(R.id.tv_nickname, sob.nickname)
            helper.setText(R.id.tv_sob, "Sob币：${sob.sob}")
            val ivRanking = helper.getView<ImageView>(R.id.iv_ranking)
            ivRanking.gone()
            when(data.indexOf(sob)){
                0 -> {
                    ivRanking.visible()
                    ivRanking.setImageResource(R.mipmap.ic_gold)
                }
                1 -> {
                    ivRanking.visible()
                    ivRanking.setImageResource(R.mipmap.ic_silver)
                }
                2 -> {
                    ivRanking.visible()
                    ivRanking.setImageResource(R.mipmap.ic_copper)
                }
            }
        }
    }
}