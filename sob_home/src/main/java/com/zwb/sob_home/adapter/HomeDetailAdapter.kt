package com.zwb.sob_home.adapter

import android.app.Activity
import android.text.Spannable
import android.text.SpannableStringBuilder
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
import com.zwb.lib_common.constant.Constants
import com.zwb.sob_home.bean.ArticleRecommendBean
import com.zwb.sob_home.bean.CommentBean
import com.zwb.lib_common.bean.TitleMultiBean
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.lib_common.view.AvatarDecorView
import com.zwb.sob_home.R


class HomeDetailAdapter(context: Activity, data: MutableList<MultiItemEntity>?) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    private val w13dp = UIUtils.dp2px(13f)
    private val w50dp = UIUtils.dp2px(50f)

    private val yourForegroundColorSpan =
        ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary))
    private val beForegroundColorSpan =
        ForegroundColorSpan(ContextCompat.getColor(context, R.color.colorPrimary))

    init {
        addItemType(Constants.MultiItemType.TYPE_TITLE, R.layout.common_adapter_title)
        addItemType(Constants.MultiItemType.TYPE_COMMENT, R.layout.common_adapter_comment)
        addItemType(Constants.MultiItemType.TYPE_SUB_COMMENT, R.layout.common_adapter_sub_comment)
        addItemType(Constants.MultiItemType.TYPE_RECOMMEND, R.layout.home_detail_adapter_related)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        item?.let {
            when (helper.itemViewType) {
                Constants.MultiItemType.TYPE_TITLE -> {
                    val title = it as TitleMultiBean
                    helper.setText(R.id.tv_title, title.title)
                }
                Constants.MultiItemType.TYPE_COMMENT -> {
                    val comment = it as CommentBean
                    helper.setText(R.id.tv_comment_nickname, comment.nickname)
                    helper.setText(R.id.tv_publishTime, comment.publishTime)
                    helper.setText(R.id.tv_comment, comment.commentContent)
                    val ivAvatar = helper.getView<AvatarDecorView>(R.id.iv_comment_avatar)
                    ivAvatar.loadAvatar(comment.vip,comment.avatar)
                    helper.addOnClickListener(R.id.tv_comment_nickname, R.id.iv_comment_avatar)
                }
                Constants.MultiItemType.TYPE_SUB_COMMENT -> {
                    val subComment = it as SubCommentBean
                    val yourNickname = subComment.yourNickname + ""
                    val beNickname = subComment.beNickname + ""
                    val str = yourNickname + " 回复 @" + beNickname + " " + subComment.content

                    val spannableString = SpannableStringBuilder()
                    spannableString.append(str)

                    spannableString.setSpan(
                        getClickSpanListener(subComment.yourUid),
                        0,
                        yourNickname.length,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )

                    spannableString.setSpan(
                        yourForegroundColorSpan, 0, yourNickname.length,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )

                    val index2 = str.indexOf("@")
                    spannableString.setSpan(
                        getClickSpanListener(subComment.beUid),
                        index2,
                        index2 + beNickname.length + 1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    spannableString.setSpan(
                        beForegroundColorSpan, index2, index2 + beNickname.length + 1,
                        Spannable.SPAN_INCLUSIVE_INCLUSIVE
                    )
                    val tvSubComment = helper.getView<TextView>(R.id.tv_sub_comment)

                    tvSubComment.text = spannableString
                    tvSubComment.movementMethod = LinkMovementMethod.getInstance()
                }
                Constants.MultiItemType.TYPE_RECOMMEND -> {
                    val recommend = it as ArticleRecommendBean
                    val ivAvatar = helper.getView<AvatarDecorView>(R.id.iv_related_avatar)
                    ivAvatar.loadAvatar(recommend.vip,recommend.avatar)
                    val ivCover = helper.getView<ImageView>(R.id.iv_cover)
                    BannerUtils.setBannerRound(ivCover, w13dp.toFloat())
                    if (recommend.covers.isNotEmpty()) {
                        Glide.with(ivCover)
                            .load(recommend.covers[0])
                            .placeholder(R.drawable.shape_grey_background)
                            .override(w50dp, w50dp)
                            .thumbnail(0.3f)
                            .into(ivCover)
                    }
                    helper.setText(R.id.tv_content, recommend.title)
                    helper.setText(R.id.tv_related_nickname, recommend.nickname)
                    helper.addOnClickListener(R.id.tv_related_nickname, R.id.iv_related_avatar)
                }
                else -> {
                }
            }
        }

    }

    private val clickableSpans: HashMap<String, ClickableSpan> = HashMap()
    private fun getClickSpanListener(id: String): ClickableSpan{
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