package com.zwb.sob_ucenter.activity

import android.view.Gravity
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.height
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.ktx.width
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.EventBusRegister
import com.zwb.lib_base.utils.EventBusUtils
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.event.StringEvent
import com.zwb.sob_ucenter.R
import com.zwb.sob_ucenter.UcenterApi
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.bean.MsgCountBean
import com.zwb.sob_ucenter.databinding.UcenterActivityMsgBinding

@Route(path = RoutePath.Ucenter.PAGE_MESSAGE)
class MsgCenterActivity : BaseActivity<UcenterActivityMsgBinding, UcenterViewModel>() {

    override val mViewModel by viewModels<UcenterViewModel>()

    override fun UcenterActivityMsgBinding.initView() {
        this.includeBar.ivBack.setOnClickListener { finish() }
        this.includeBar.tvTitle.text = "消息通知"
        this.includeBar.tvSearch.text = "全部已读"
        this.includeBar.tvSearch.visible()
        this.includeBar.tvSearch.setTextColor(
            ContextCompat.getColor(
                this@MsgCenterActivity,
                R.color.colorAccent
            )
        )
        this.includeBar.ivRight.gone()
        this.includeBar.tvSearch.setOnClickListener {
            msgRead()
        }
        this.tvArticle.setOnClickListener {

        }
    }

    override fun setStatusBar() {
        super.setStatusBar()
        StatusBarUtil.setPaddingSmart(this, mBinding.includeBar.toolbar)
    }

    override fun initObserve() {

    }

    override fun initRequestData() {
        mViewModel.getMsgCount(UcenterApi.USER_MSG_COUNT_URL).observe(this, {
            it?.let {
                setMsgCountData(it)
            }
        })
    }

    /**
     * 全部已读
     */
    private fun msgRead(){
        mViewModel.msgRead().observe(this,{
            if(it.success){
                initRequestData()
                EventBusUtils.postEvent(StringEvent(StringEvent.Event.MSG_READ))
            }
            toast(it.message)
        })
    }


    private fun setMsgCountData(msg: MsgCountBean) {
        if (msg.wendaMsgCount != 0) {
            mBinding.tvWenda.setRightString(if (msg.wendaMsgCount > 99) "99" else msg.wendaMsgCount.toString())
            mBinding.tvWenda.rightTextView.height(UIUtils.dp2px(16f))
            mBinding.tvWenda.rightTextView.width(UIUtils.dp2px(16f))
        }else{
            mBinding.tvWenda.rightTextView.gone()
        }

        if (msg.articleMsgCount != 0) {
            mBinding.tvArticle.setRightString(if (msg.articleMsgCount > 99) "99" else msg.articleMsgCount.toString())
            mBinding.tvArticle.rightTextView.height(UIUtils.dp2px(16f))
            mBinding.tvArticle.rightTextView.width(UIUtils.dp2px(16f))
        }else{
            mBinding.tvArticle.rightTextView.gone()
        }

        if (msg.momentCommentCount != 0) {
            mBinding.tvDynamic.setRightString(if (msg.momentCommentCount > 99) "99" else msg.momentCommentCount.toString())
            mBinding.tvDynamic.rightTextView.height(UIUtils.dp2px(16f))
            mBinding.tvDynamic.rightTextView.width(UIUtils.dp2px(16f))
        }else{
            mBinding.tvDynamic.rightTextView.gone()
        }

        if (msg.thumbUpMsgCount != 0) {
            mBinding.tvStar.setRightString(if (msg.thumbUpMsgCount > 99) "99" else msg.thumbUpMsgCount.toString())
            mBinding.tvStar.rightTextView.height(UIUtils.dp2px(16f))
            mBinding.tvStar.rightTextView.width(UIUtils.dp2px(16f))
        }else{
            mBinding.tvStar.rightTextView.gone()
        }

        if (msg.atMsgCount != 0) {
            mBinding.tvAt.setRightString(if (msg.atMsgCount > 99) "99" else msg.atMsgCount.toString())
            mBinding.tvAt.rightTextView.height(UIUtils.dp2px(16f))
            mBinding.tvAt.rightTextView.width(UIUtils.dp2px(16f))
        }else{
            mBinding.tvAt.rightTextView.gone()
        }

        if (msg.systemMsgCount != 0) {
            mBinding.tvSystem.setRightString(if (msg.systemMsgCount > 99) "99" else msg.systemMsgCount.toString())
            mBinding.tvSystem.rightTextView.height(UIUtils.dp2px(16f))
            mBinding.tvSystem.rightTextView.width(UIUtils.dp2px(16f))
        }else{
            mBinding.tvSystem.rightTextView.gone()
        }
    }

}