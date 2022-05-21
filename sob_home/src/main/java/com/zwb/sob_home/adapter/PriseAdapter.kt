package com.zwb.sob_home.adapter

import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_base.utils.DateUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.view.AvatarDecorView
import com.zwb.sob_home.R
import com.zwb.sob_home.bean.PriseArticleBean
import net.mikaelzero.mojito.tools.Utils

class PriseAdapter(list:MutableList<PriseArticleBean?>):
    BaseQuickAdapter<PriseArticleBean, BaseViewHolder>(R.layout.home_adapter_prise,list) {

    override fun convert(helper: BaseViewHolder, item: PriseArticleBean?) {
        item?.let {
            val ivAvatar = helper.getView<AvatarDecorView>(R.id.iv_avatar)
            ivAvatar.loadAvatar(it.vip,it.avatar)
            helper.setText(R.id.tv_nickname,it.nickname)
            val str = "打赏 ${it.sob} Sob币"
            helper.setText(R.id.tv_prise_value,UIUtils.setTextViewContentStyle(
                str,
                AbsoluteSizeSpan(UIUtils.dp2px(16f)),
                ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.colorWarning)),
                3, it.sob.toString().length+3
            ))
            helper.setText(R.id.tv_time, DateUtils.timeFormat(it.createTime))
        }
    }
}