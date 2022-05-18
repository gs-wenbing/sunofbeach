package com.zwb.sob_ucenter.adapter

import android.widget.Button
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.youth.banner.util.BannerUtils
import com.zwb.lib_common.view.AvatarDecorView
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.sob_ucenter.R
import com.zwb.sob_ucenter.bean.FollowBean
import org.checkerframework.common.subtyping.qual.Bottom

class FollowAdapter(list: MutableList<FollowBean?>) :
    BaseQuickAdapter<FollowBean, BaseViewHolder>(R.layout.ucenter_adapter_follow, list) {

    override fun convert(helper: BaseViewHolder, item: FollowBean?) {
        item?.let { follow ->
            val ivAvatar = helper.getView<AvatarDecorView>(R.id.iv_avatar)
            ivAvatar.loadAvatar(follow.vip,follow.avatar)

            helper.setText(R.id.tv_nickname, follow.nickname)
            helper.setText(R.id.tv_sign, follow.sign)
//            relative对应的值:
//            0表示没有关注对方，可以显示为：关注
//            1表示对方关注自己，可以显示为：回粉
//            2表示已经关注对方，可以显示为：已关注
//            3表示相互关注，可以显示为：相互关注
            val btnFollow = helper.getView<Button>(R.id.btn_follow_state)
            CommonViewUtils.setFollowState(btnFollow,follow.relative)
            helper.addOnClickListener(R.id.btn_follow_state)
        }
    }
}