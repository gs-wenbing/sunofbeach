package com.zwb.sob_ucenter.adapter

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_common.view.AvatarDecorView
import com.zwb.sob_ucenter.R
import com.zwb.sob_ucenter.bean.MsgAtBean
import com.zwb.sob_ucenter.bean.MsgBean
import com.zwb.sob_ucenter.bean.MsgMomentBean
import com.zwb.sob_ucenter.bean.MsgThumbBean

class MsgListAdapter(list: MutableList<MsgBean?>) :
    BaseQuickAdapter<MsgBean, BaseViewHolder>(R.layout.ucenter_adapter_msg_list, list) {

    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: MsgBean?) {
        item?.let {
            val ivAvatar = helper.getView<AvatarDecorView>(R.id.iv_avatar)
            val tvNickname = helper.getView<TextView>(R.id.tv_nickname)
            val tvCreateTime = helper.getView<TextView>(R.id.tv_createTime)
            val tvContent = helper.getView<TextView>(R.id.tv_content)
            val tvReplyTitle = helper.getView<TextView>(R.id.tv_reply_title)
            val tvReplyValue = helper.getView<TextView>(R.id.tv_reply_value)
            val tvReplyState = helper.getView<TextView>(R.id.tv_reply_state)
            helper.addOnClickListener(R.id.iv_avatar)
            when (it) {
                is MsgThumbBean -> {
                    ivAvatar.loadAvatar(false,it.avatar)
                    tvNickname.text = it.nickname
                    tvCreateTime.text = it.timeText
                    tvReplyTitle.text = "给朕的内容点赞："
                    tvReplyValue.text = "「${it.title}」"
                    tvContent.gone()
                    tvReplyState.gone()
                }
                is MsgAtBean -> {
                    ivAvatar.loadAvatar(false,it.avatar)
                    tvNickname.text = it.nickname
                    tvCreateTime.text = it.publishTime
                    // 回复了我的评论：「点击查看详情」 未读
                    tvReplyTitle.text = "回复了我的评论："
                    tvReplyValue.text = "「点击查看详情」"
                    tvContent.text = it.content
                    if(it.hasRead=="0"){
                        tvReplyState.text = "未读"
                        tvReplyState.setTextColor(ContextCompat.getColor(mContext,R.color.colorAccent))
                        tvReplyState.setBackgroundResource(R.drawable.blue_hollow_btn_normal)
                    }else{
                        tvReplyState.text = "已阅"
                        tvReplyState.setTextColor(ContextCompat.getColor(mContext,R.color.white))
                        tvReplyState.setBackgroundResource(R.drawable.green_solid_btn_normal)
                    }
                }
                is MsgMomentBean -> {
                    ivAvatar.loadAvatar(false,it.avatar)
                    tvNickname.text = it.nickname
                    tvCreateTime.text = it.createTime
                    //评论了我的动态：「 上班之余，做了个阳光沙滩app<img class="emoji" src="https://cdn.sunofbeaches.... 」 未读
                    tvReplyTitle.text = "评论了我的动态："
                    tvReplyValue.text = "「${it.title}」"
                    tvContent.text = it.content
                    if(it.hasRead=="0"){
                        tvReplyState.text = "未读"
                        tvReplyState.setTextColor(ContextCompat.getColor(mContext,R.color.colorAccent))
                        tvReplyState.setBackgroundResource(R.drawable.blue_hollow_btn_normal)
                    }else{
                        tvReplyState.text = "已阅"
                        tvReplyState.setTextColor(ContextCompat.getColor(mContext,R.color.white))
                        tvReplyState.setBackgroundResource(R.drawable.green_solid_btn_normal)
                    }

                }
            }
        }
    }
}