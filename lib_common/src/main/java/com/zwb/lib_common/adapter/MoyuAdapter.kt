package com.zwb.lib_common.adapter

import android.graphics.Typeface
import android.text.TextUtils
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.allen.library.SuperTextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.utils.DateUtils
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.R
import com.zwb.lib_common.bean.MoyuItemBean
import com.zwb.lib_common.constant.SpKey
import com.zwb.lib_common.view.AvatarDecorView
import com.zwb.lib_common.view.CommonViewUtils


class MoyuAdapter(val fragment: Fragment, data: MutableList<MoyuItemBean>?) :
    BaseQuickAdapter<MoyuItemBean, BaseViewHolder>(R.layout.common_moyu_adapter, data) {

    override fun convert(helper: BaseViewHolder, item: MoyuItemBean?) {
        item?.let {
            // 用户
            val ivAvatar = helper.getView<AvatarDecorView>(R.id.iv_avatar)
            ivAvatar.loadAvatar(it.vip,it.avatar)

            helper.setText(R.id.tv_nickname,it.nickname)
            var desc = if (TextUtils.isEmpty(it.position)) "" else it.position
            desc = if (TextUtils.isEmpty(it.company)) desc else "${desc}@${it.company}"
            helper.setText(R.id.tv_position,desc)
            helper.setText(R.id.tv_publishTime,DateUtils.timeFormat(it.createTime))

            if (it.vip) {
                helper.getView<TextView>(R.id.tv_nickname).setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.colorWarning
                    )
                )
            } else {
                helper.getView<TextView>(R.id.tv_nickname).setTextColor(
                    ContextCompat.getColor(
                        mContext,
                        R.color.textPrimary
                    )
                )
            }
            // 内容
            CommonViewUtils.setHtml(helper.getView(R.id.tv_content),it.content)

            // 链接
            val tvLink = helper.getView<SuperTextView>(R.id.tv_link)
            if (!TextUtils.isEmpty(it.linkTitle) && !TextUtils.isEmpty(it.linkUrl)) {
                tvLink.visible()
                tvLink.setLeftTopString(it.linkTitle)
                tvLink.setLeftString(it.linkUrl)
                tvLink.leftTextView.setTypeface(null, Typeface.ITALIC)
            } else {
                tvLink.gone()
            }

            val rvPic: RecyclerView = helper.getView(R.id.rv_pic)
            if (it.images.isNotEmpty()) {
                rvPic.visible()
                setImageData(it.images, rvPic)
            } else {
                rvPic.gone()
            }

            val tvTopicName = helper.getView<TextView>(R.id.tv_topic_name)
            if (!TextUtils.isEmpty(it.topicName)) {
                tvTopicName.visible()
                tvTopicName.text = it.topicName
            } else {
                tvTopicName.gone()
            }

            helper.setText(R.id.tv_star, it.thumbUpList.size.toString())
            helper.setText(R.id.tv_reply, it.commentCount.toString())
            helper.addOnClickListener(R.id.iv_avatar,R.id.tv_nickname,R.id.tv_link)
            // 判断是否已经点赞该条动态
            val loginUserId = SpUtils.getString(SpKey.USER_ID,"")
            CommonViewUtils.setThumbStyle(helper.getView(R.id.tv_star),it.thumbUpList.contains(loginUserId))
        }
    }

    private var width2 = (UIUtils.getScreenWidth() - UIUtils.dp2px(44f)) / 2
    private var width3 = (UIUtils.getScreenWidth() - UIUtils.dp2px(44f)) / 3

    private fun setImageData(pics: List<String>, rvPic: RecyclerView) {
        var width = width2
        when {
            (pics.size in 1..2) -> {
                width = width2
                rvPic.layoutManager = GridLayoutManager(mContext, 2)
            }
            pics.size >= 3 -> {
                width = width3
                rvPic.layoutManager = GridLayoutManager(mContext, 3)
            }
        }
        val adapter = ImageAdapter(width, pics.toMutableList())
        rvPic.adapter = adapter
        adapter.setOnItemClickListener { _, _, position ->
            CommonViewUtils.showBigImage(rvPic,R.id.iv_image, pics, position)
        }
    }
}