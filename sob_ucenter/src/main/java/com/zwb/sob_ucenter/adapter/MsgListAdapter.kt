package com.zwb.sob_ucenter.adapter

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_common.view.AvatarDecorView
import com.zwb.sob_ucenter.R
import com.zwb.sob_ucenter.bean.*

class MsgListAdapter(list: MutableList<MsgBean?>) :
    BaseQuickAdapter<MsgBean, BaseViewHolder>(R.layout.ucenter_adapter_msg_list, list) {

    @SuppressLint("SetTextI18n")
    override fun convert(helper: BaseViewHolder, item: MsgBean?) {
        item?.let {
            helper.addOnClickListener(R.id.iv_avatar)
            when (it) {
                is MsgThumbBean -> {
                    setThumbData(helper,it)
                }
                is MsgAtBean -> {
                    setAtData(helper,it)
                }
                is MsgMomentBean -> {
                    setMomentData(helper,it)
                }
                is MsgArticleBean -> {
                    setArticleData(helper,it)
                }
            }
        }
    }

    private fun setThumbData(helper: BaseViewHolder,it: MsgThumbBean){
        helper.getView<AvatarDecorView>(R.id.iv_avatar).loadAvatar(false,it.avatar)
        helper.setText(R.id.tv_nickname,it.nickname)
        helper.setText(R.id.tv_createTime,it.timeText)
        helper.setText(R.id.tv_reply_title, "给朕的内容点赞：")
        helper.setText(R.id.tv_reply_value, "「${it.title}」")
        helper.getView<TextView>(R.id.tv_content).gone()
        helper.getView<TextView>(R.id.tv_reply_state).gone()
    }

    private fun setAtData(helper: BaseViewHolder,it: MsgAtBean){
        helper.getView<AvatarDecorView>(R.id.iv_avatar).loadAvatar(false,it.avatar)
        helper.setText(R.id.tv_nickname,it.nickname)
        helper.setText(R.id.tv_createTime,it.publishTime)
        helper.setText(R.id.tv_reply_title, "回复了我的评论：")
        helper.setText(R.id.tv_reply_value, "「点击查看详情」")
        helper.setText(R.id.tv_content, it.content)
        setState(helper.getView(R.id.tv_reply_state),it.hasRead)
    }

    private fun setMomentData(helper: BaseViewHolder,it: MsgMomentBean){
        helper.getView<AvatarDecorView>(R.id.iv_avatar).loadAvatar(false,it.avatar)
        helper.setText(R.id.tv_nickname,it.nickname)
        helper.setText(R.id.tv_createTime,it.createTime)
        //评论了我的文章：「 上班之余，做了个阳光沙滩app<img class="emoji" src="https://cdn.sunofbeaches.... 」 未读
        helper.setText(R.id.tv_reply_title, "评论了我的动态：")
        helper.setText(R.id.tv_reply_value, "「${it.title}」")
        helper.setText(R.id.tv_content, it.content)
        setState(helper.getView(R.id.tv_reply_state),it.hasRead)
    }


    private fun setArticleData(helper: BaseViewHolder,it: MsgArticleBean){
        helper.getView<AvatarDecorView>(R.id.iv_avatar).loadAvatar(false,it.avatar)
        helper.setText(R.id.tv_nickname,it.nickname)
        helper.setText(R.id.tv_createTime,it.createTime)
        //评论了我的文章：「 上班之余，做了个阳光沙滩app<img class="emoji" src="https://cdn.sunofbeaches.... 」 未读
        helper.setText(R.id.tv_reply_title, "评论了我的文章：")
        helper.setText(R.id.tv_reply_value, "「${it.title}」")
        helper.setText(R.id.tv_content, it.content)
        setState(helper.getView(R.id.tv_reply_state),it.hasRead)
    }

    private fun setState(tvReplyState: TextView,hasRead:String){
        if(hasRead=="0"){
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