package com.zwb.sob_ucenter.adapter

import android.widget.ImageView
import androidx.annotation.LayoutRes
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.utils.DateUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.view.AvatarDecorView
import com.zwb.sob_ucenter.R
import com.zwb.sob_ucenter.bean.ArticleBean
import com.zwb.sob_ucenter.bean.ShareBean
import com.zwb.sob_ucenter.bean.UserWendaBean

class UserCenterArticleAdapter(@LayoutRes layoutId:Int, data: MutableList<MultiItemEntity>?) :
    BaseQuickAdapter<MultiItemEntity, BaseViewHolder>(layoutId,data) {

    private val w50dp = UIUtils.dp2px(50f)
    private val w10dp = UIUtils.dp2px(10f)
    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        item?.let { it->
            when (it) {
                is ArticleBean -> {
                    helper.setText(R.id.tv_content, it.title)
                    helper.setText(R.id.tv_star, it.thumbUp.toString())
                    helper.setText(R.id.tv_time, DateUtils.timeFormat(it.createTime))
                    helper.setText(R.id.tv_labels, it.labels())
                    val ivCover = helper.getView<ImageView>(R.id.iv_cover)
                    BannerUtils.setBannerRound(ivCover, 10f)
                    Glide.with(ivCover.context).load(it.covers[0])
                        .placeholder(R.drawable.shape_grey_background)
                        .override(w50dp)
                        .thumbnail(0.3f)
                        .into(ivCover)
                }
                is ShareBean -> {
                    helper.setText(R.id.tv_title, it.title)
                    helper.setText(R.id.tv_nickname, it.nickname)
                    helper.setText(R.id.tv_time, DateUtils.timeFormat(it.createTime))
                    helper.setText(R.id.tv_labels, it.labels(isSub = false))
                    helper.setText(R.id.tv_star, it.thumbUp.toString())
                    helper.setText(R.id.tv_viewCount, it.viewCount.toString())
                    val ivAvatar = helper.getView<AvatarDecorView>(R.id.iv_avatar)
                    ivAvatar.loadAvatar(it.vip,it.avatar)

                    val ivCover = helper.getView<ImageView>(R.id.iv_cover)
                    BannerUtils.setBannerRound(ivCover, 10f)
                    Glide.with(ivCover.context).load(it.cover)
                        .placeholder(R.drawable.shape_grey_background)
                        .override(w50dp)
                        .thumbnail(0.3f)
                        .into(ivCover)
                }
                is UserWendaBean->{
                    helper.setText(R.id.tv_title, it.wendaTitle)
                    helper.setText(R.id.tv_time, DateUtils.timeFormat(it.wendaComment.publishTime))
                }
                else -> {

                }
            }

        }
    }


}