package com.zwb.sob_home.adapter

import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.utils.DateUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.view.AvatarDecorView
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.sob_home.R
import com.zwb.sob_home.bean.BannerBean
import com.zwb.sob_home.bean.BannerList
import com.zwb.sob_home.bean.HomeItemBean


class HomeAdapter(data: MutableList<MultiItemEntity>?) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        addItemType(1, R.layout.home_adapter)
        addItemType(2, R.layout.home_multi_adapter)
    }


    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        item.let {

            val bean = item as HomeItemBean
            helper.setText(R.id.tv_content, bean.title)
            helper.setText(R.id.tv_nickName, bean.nickName)
            helper.setText(R.id.tv_viewCount, bean.viewCount.toString())
            helper.setText(R.id.tv_star, bean.thumbUp.toString())
            helper.setText(R.id.tv_time, DateUtils.timeFormat(bean.createTime))

            val ivAvatar = helper.getView<AvatarDecorView>(R.id.iv_avatar)
            ivAvatar.loadAvatar(bean.isVip,bean.avatar)

            if (helper.itemViewType == 1) {
                val ivCover = helper.getView<ImageView>(R.id.iv_cover)
                BannerUtils.setBannerRound(ivCover, 10f)
                Glide.with(ivCover.context).load(bean.covers[0])
                    .placeholder(R.drawable.shape_grey_background)
                    .override(w50dp)
                    .thumbnail(0.3f)
                    .into(ivCover)
            } else {
                val rvPic: RecyclerView = helper.getView(R.id.rv_pic)
                rvPic.layoutManager = GridLayoutManager(mContext, 3)
                val adapter = ImageAdapter(w, bean.covers.toMutableList())
                rvPic.adapter = adapter
                adapter.setOnItemClickListener { _, _, position ->
                    CommonViewUtils.showBigImage(rvPic,R.id.iv_image, bean.covers, position)
                }
            }

            helper.addOnClickListener(
                R.id.tv_content,
                R.id.iv_avatar,
                R.id.tv_nickName,
                R.id.iv_cover
            )
        }
    }

    private val w26dp = UIUtils.dp2px(26f)
    private val w50dp = UIUtils.dp2px(50f)
    private val w = (UIUtils.getScreenWidth() - UIUtils.dp2px(44f)) / 3
}
