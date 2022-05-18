package com.zwb.sob_wenda.adapter

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.bean.SubCommentBean
import com.zwb.lib_common.bean.TitleMultiBean
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.view.AvatarDecorView
import com.zwb.sob_wenda.R

class WendaAnswerAdapter(data: MutableList<MultiItemEntity>?) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    private val w20dp = UIUtils.dp2px(20f)

    init {
        addItemType(Constants.MultiItemType.TYPE_TITLE, R.layout.common_adapter_title)
        addItemType(Constants.MultiItemType.TYPE_SUB_COMMENT, R.layout.wenda_adapter_comment)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        item?.let {
            when (helper.itemViewType) {
                Constants.MultiItemType.TYPE_TITLE -> {
                    val title = it as TitleMultiBean
                    helper.setText(R.id.tv_title, title.title)
                }

                Constants.MultiItemType.TYPE_SUB_COMMENT -> {
                    val subComment = it as SubCommentBean

                    helper.setText(R.id.tv_your_nickname, subComment.yourNickname)
                    helper.setText(R.id.tv_be_nickname, " 回复 @${subComment.beNickname}")
                    helper.setText(R.id.tv_publishTime, subComment.publishTime)
                    helper.setText(R.id.tv_comment, subComment.content)
                    val ivAvatar = helper.getView<AvatarDecorView>(R.id.iv_your_avatar)
                    ivAvatar.loadAvatar(subComment.vip,subComment.yourAvatar)
                    helper.addOnClickListener(R.id.tv_your_nickname, R.id.iv_your_avatar,R.id.tv_be_nickname)
                }
                else ->{}
            }
        }
    }

}