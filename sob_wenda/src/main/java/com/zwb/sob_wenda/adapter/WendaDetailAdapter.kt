package com.zwb.sob_wenda.adapter

import android.text.Html
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.utils.DateUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.bean.TitleMultiBean
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.view.AvatarDecorView
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.lib_common.view.HtmlImageGetter
import com.zwb.sob_wenda.R
import com.zwb.sob_wenda.bean.AnswerBean
import com.zwb.sob_wenda.bean.WendaBean

class WendaDetailAdapter(data: MutableList<MultiItemEntity>?) :
    BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    private val w26dp = UIUtils.dp2px(26f)

    init {
        addItemType(Constants.MultiItemType.TYPE_TITLE, R.layout.common_adapter_title)
        addItemType(Constants.MultiItemType.TYPE_COMMENT, R.layout.wenda_detail_answer_adapter)
        addItemType(Constants.MultiItemType.TYPE_RECOMMEND, R.layout.wenda_detail_answer_adapter)
    }

    override fun convert(helper: BaseViewHolder, item: MultiItemEntity?) {
        item?.let {
            when (helper.itemViewType) {
                Constants.MultiItemType.TYPE_TITLE -> {
                    val title = it as TitleMultiBean
                    helper.setText(R.id.tv_title, title.title)
                }
                Constants.MultiItemType.TYPE_COMMENT -> {
                    // 回答item
                    val comment = it as AnswerBean
                    // 昵称
                    helper.setText(R.id.tv_comment_nickname, comment.nickname)
                    // 发布时间
                    helper.setText(R.id.tv_publishTime, DateUtils.timeFormat(comment.publishTime))
                    // 回答内容（显示一部分）
                    CommonViewUtils.setHtml(helper.getView(R.id.tv_comment),comment.content)
                    //隐藏浏览量
                    helper.getView<View>(R.id.tv_viewCount).gone()
                    //回答的点赞数
                    helper.setText(R.id.tv_thumb,comment.thumbUp.toString())
                    // 回答的评论数
                    helper.setText(R.id.tv_reply, "${comment.wendaSubComments.size} 评论")
                    val ivAvatar = helper.getView<AvatarDecorView>(R.id.iv_comment_avatar)
                    ivAvatar.loadAvatar(comment.isVip,comment.avatar)
                    // 是否是最近答案
                    if(comment.bestAs=="1"){
                        helper.getView<View>(R.id.tv_solved).visible()
                    }else{
                        helper.getView<View>(R.id.tv_solved).gone()
                    }
                    helper.addOnClickListener(R.id.tv_comment_nickname, R.id.iv_comment_avatar)
                }
                Constants.MultiItemType.TYPE_RECOMMEND -> {
                    // 相关推荐item
                    val wenda = it as WendaBean
                    // 昵称
                    helper.setText(R.id.tv_comment_nickname, wenda.nickname)
                    // 发布时间
                    helper.setText(R.id.tv_publishTime,  DateUtils.timeFormat(wenda.createTime))
                    // 发布标题
                    helper.setText(R.id.tv_comment, wenda.title)
                    val ivAvatar = helper.getView<AvatarDecorView>(R.id.iv_comment_avatar)
                    ivAvatar.loadAvatar(wenda.isVip=="1",wenda.avatar)
                    helper.addOnClickListener(R.id.tv_comment_nickname, R.id.iv_comment_avatar)
                    // 隐藏查看详情（和回答的item共有一个layout）
                    helper.getView<View>(R.id.tv_more).gone()
                    // 浏览量
                    helper.setText(R.id.tv_viewCount,wenda.viewCount.toString())
                    // 隐藏问题的点赞数
                    helper.getView<View>(R.id.tv_thumb).gone()
                    // 回答数
                    helper.setText(R.id.tv_reply, "${wenda.answerCount} 回答")
                    // 是否已经解决
                    if(wenda.isResolve=="1"){
                        helper.setText(R.id.tv_solved, "已解决")
                        helper.getView<View>(R.id.tv_solved).visible()
                    }else{
                        helper.getView<View>(R.id.tv_solved).gone()
                    }
                }
                else ->{}
            }
        }
    }
}