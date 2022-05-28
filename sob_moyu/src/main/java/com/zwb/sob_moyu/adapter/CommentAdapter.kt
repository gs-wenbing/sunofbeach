package com.zwb.sob_moyu.adapter

import android.app.Activity
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.bean.SubCommentBean
import com.zwb.lib_common.bean.TitleMultiBean
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.lib_common.view.AvatarDecorView
import com.zwb.sob_moyu.R
import com.zwb.sob_moyu.bean.MomentCommentBean
import com.zwb.sob_moyu.bean.MomentSubComment

class CommentAdapter(context: Activity, data: MutableList<MultiItemEntity>?) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    private val yourForegroundColorSpan =
        ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary))
    private val beForegroundColorSpan =
        ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary))

    init {
        addItemType(Constants.MultiItemType.TYPE_TITLE, R.layout.common_adapter_title)
        addItemType(Constants.MultiItemType.TYPE_COMMENT, R.layout.common_adapter_comment)
        addItemType(Constants.MultiItemType.TYPE_SUB_COMMENT, R.layout.common_adapter_sub_comment)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        item?.let {
            when (helper.itemViewType) {
                Constants.MultiItemType.TYPE_TITLE -> {
                    val title = it as TitleMultiBean
                    helper.setText(R.id.tv_title, title.title)
                }
                Constants.MultiItemType.TYPE_COMMENT -> {
                    val comment = it as MomentCommentBean
                    helper.setText(R.id.tv_comment_nickname, comment.nickname)
                    helper.setText(R.id.tv_publishTime, comment.createTime)
                    helper.setText(R.id.tv_comment, comment.content)
                    val ivAvatar = helper.getView<AvatarDecorView>(R.id.iv_comment_avatar)
                    ivAvatar.loadAvatar(comment.vip, comment.avatar)
                    helper.addOnClickListener(R.id.tv_comment_nickname, R.id.iv_comment_avatar,R.id.iv_comment_reply)
                }
                Constants.MultiItemType.TYPE_SUB_COMMENT -> {
                    val subComment = it as MomentSubComment
                    val yourNickname = subComment.nickname + ""
                    val beNickname = subComment.targetUserNickname + ""
                    val str = yourNickname + " 回复 @" + beNickname + " " + subComment.content

                    val spannableString = SpannableStringBuilder()
                    spannableString.append(str)

                    spannableString.setSpan(
                        getClickSpanListener(subComment.userId),
                        0,
                        yourNickname.length,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )

                    spannableString.setSpan(
                        yourForegroundColorSpan, 0, yourNickname.length,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )

                    val index2 = str.indexOf("@")
                    if (!TextUtils.isEmpty(subComment.targetUserId)) {
                        spannableString.setSpan(
                            getClickSpanListener(subComment.targetUserId!!),
                            index2,
                            index2 + beNickname.length + 1,
                            Spannable.SPAN_INCLUSIVE_INCLUSIVE
                        )
                    }
                    spannableString.setSpan(
                        beForegroundColorSpan, index2, index2 + beNickname.length + 1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    val tvSubComment = helper.getView<TextView>(R.id.tv_sub_comment)

                    tvSubComment.text = spannableString
                    tvSubComment.movementMethod = LinkMovementMethod.getInstance()
                }
                else -> {
                }
            }
        }
    }

    private val clickableSpans: HashMap<String, ClickableSpan> = HashMap()
    private fun getClickSpanListener(id: String): ClickableSpan {
        var clickableSpan = clickableSpans[id]
        if (clickableSpan == null) {
            clickableSpan = object : ClickableSpan() {
                override fun onClick(widget: View) {
                    UcenterServiceWrap.instance.launchDetail(id)
                }
            }
            clickableSpans[id] = clickableSpan
        }
        return clickableSpan
    }

}