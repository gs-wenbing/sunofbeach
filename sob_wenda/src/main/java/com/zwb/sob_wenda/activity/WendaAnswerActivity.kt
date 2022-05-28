package com.zwb.sob_wenda.activity

import android.annotation.SuppressLint
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.webkit.JavascriptInterface
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.DateUtils
import com.zwb.lib_base.utils.EventBusUtils
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.adapter.CommonPriseAdapter
import com.zwb.lib_common.bean.PriseSobBean
import com.zwb.lib_common.bean.SubCommentBean
import com.zwb.lib_common.bean.TitleMultiBean
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.databinding.CommonPriseDialogBinding
import com.zwb.lib_common.event.UpdateItemEvent
import com.zwb.lib_common.service.home.wrap.HomeServiceWrap
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.lib_common.view.FixedHeightBottomSheetDialog
import com.zwb.lib_common.view.ReplyBottomSheetDialog
import com.zwb.sob_wenda.R
import com.zwb.sob_wenda.WendaApi
import com.zwb.sob_wenda.WendaViewModel
import com.zwb.sob_wenda.adapter.WendaAnswerAdapter
import com.zwb.sob_wenda.bean.AnswerBean
import com.zwb.sob_wenda.bean.AnswerInputBean
import com.zwb.sob_wenda.bean.WendaContentBean
import com.zwb.sob_wenda.bean.WendaSubCommentInputBean
import com.zwb.sob_wenda.databinding.WendaActivityAnswerBinding
import com.zwb.sob_wenda.databinding.WendaDetailHeaderBinding

@Route(path = RoutePath.Wenda.PAGE_ANSWER_DETAIL)
class WendaAnswerActivity : BaseActivity<WendaActivityAnswerBinding, WendaViewModel>() {

    override val mViewModel by viewModels<WendaViewModel>()

    @JvmField
    @Autowired(name = RoutePath.Wenda.PARAMS_WENDA_CONTENT)
    var wendaContent: WendaContentBean? = null

    @JvmField
    @Autowired(name = RoutePath.Wenda.PARAMS_ANSWER)
    var answerBean: AnswerBean? = null

    private lateinit var contentBinding: WendaDetailHeaderBinding

    private lateinit var mAdapter: WendaAnswerAdapter

    private lateinit var bottomSheetDialog: FixedHeightBottomSheetDialog

    private lateinit var replyDialog: ReplyBottomSheetDialog

    override fun WendaActivityAnswerBinding.initView() {
        mBinding.includeBar.tvSearch.gone()
        mBinding.includeBar.ivBack.setOnClickListener { finish() }

        contentBinding = WendaDetailHeaderBinding.inflate(layoutInflater)
        contentBinding.tvLabels.gone()
        contentBinding.layoutVisibility.visible()

        mAdapter = WendaAnswerAdapter(mutableListOf())
        this.rvContent.setHasFixedSize(true)
        this.rvContent.adapter = mAdapter
        this.rvContent.layoutManager = LinearLayoutManager(this@WendaAnswerActivity)
        mAdapter.addHeaderView(contentBinding.root)

        bottomSheetDialog = FixedHeightBottomSheetDialog(
            this@WendaAnswerActivity,
            R.style.BottomSheetDialog,
            UIUtils.getScreenHeight() * 2 / 3
        )
        replyDialog = ReplyBottomSheetDialog(
            this@WendaAnswerActivity,
            R.style.BottomSheetDialog
        )
        initListener()
        setViewData()
    }

    override fun setStatusBar() {
        super.setStatusBar()
        StatusBarUtil.setPaddingSmart(this, mBinding.includeBar.toolbar)
        StatusBarUtil.setPaddingSmart(this, mBinding.layoutHeaderAvatar)
    }

    private fun initListener() {
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position)
            if (item is SubCommentBean) {
                when (view.id) {
                    R.id.tv_your_nickname, R.id.iv_your_avatar -> {
                        UcenterServiceWrap.instance.launchDetail(item.yourUid)
                    }
                    R.id.tv_be_nickname -> {
                        UcenterServiceWrap.instance.launchDetail(item.beUid)
                    }
                    R.id.iv_comment_reply -> {
                        showReplyDialog(item)
                    }
                }
            }

        }
        mBinding.tvHeaderFollow.setOnClickListener {
            if (isLoginIntercept(true) && answerBean != null) {
                follow(answerBean!!.uid)
            }
        }
        mBinding.ivHeaderAvatar.setOnClickListener {
            if (answerBean != null) {
                UcenterServiceWrap.instance.launchDetail(answerBean!!.uid)
            }
        }

        mBinding.tvReply.setOnClickListener {
            showReplyDialog()
        }
        mBinding.tvThumb.setOnClickListener {
            if (isLoginIntercept(true) && it.tag == null && answerBean != null) {
                commentThumb()
            }
        }
        mBinding.tvReward.setOnClickListener {
            showPriseDialog()
        }
    }

    override fun initObserve() {
    }

    override fun initRequestData() {

    }

    @SuppressLint("SetTextI18n", "JavascriptInterface")
    private fun setViewData(){
        answerBean?.let {
            mBinding.tvHeaderNickname.text = it.nickname
            mBinding.ivHeaderAvatar.loadAvatar(it.isVip, it.avatar)
            contentBinding.codeView.showCode(it.content)
            contentBinding.codeView.addJavascriptInterface(this, "native")
            contentBinding.codeView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    CommonViewUtils.toWebView(request.url.toString())
                    return true
                }
            }
            mAdapter.addData(TitleMultiBean("评论（ ${it.wendaSubComments.size}）"))
            mAdapter.addData(it.wendaSubComments)
            getFollowState(it.uid)

            mBinding.tvThumb.text = it.thumbUp.toString()
            commentThumbCheck(it._id)
        }
        wendaContent?.let {
            if (it.isResolve == "1") {
                val title = "【已解决】${it.title}"
                contentBinding.tvTitle.text = UIUtils.setTextViewContentStyle(
                    title,
                    AbsoluteSizeSpan(UIUtils.dp2px(14f)),
                    ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorSuccess)),
                    0, 5
                )
            } else {
                contentBinding.tvTitle.text = it.title
            }
            contentBinding.tvSob.text = "${it.sob} 币"
            contentBinding.tvView.text = "${it.viewCount} 浏览"
            contentBinding.tvPublishTime.text = "发表于 ${DateUtils.timeFormat(it.createTime)}"
            val str = "提问者 @${it.nickname}"
            contentBinding.tvQuestioner.text = UIUtils.setTextViewContentStyle(
                str,
                AbsoluteSizeSpan(UIUtils.dp2px(14f)),
                ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)),
                str.indexOf("@"), str.length
            )
        }
    }

    @JavascriptInterface
    fun showBigImage(index: Int) {
        CommonViewUtils.showBigImage(this, contentBinding.codeView.getImageList(), index)
    }

    private fun getFollowState(userId: String) {
        if (!isLoginIntercept(false)) {
            return
        }
        mViewModel.followState(userId).observe(this, {
            mBinding.tvHeaderFollow.visible()
            if (it.success && it.data != null) {
                CommonViewUtils.setFollowState(mBinding.tvHeaderFollow, it.data!!)
            }
        })
    }


    /**
     * 关注
     */
    private fun follow(userId: String) {
        mViewModel.follow(userId).observe(this, {
            getFollowState(userId)
        })
    }

    /**
     * 检查是否有点赞某个回答
     */
    private fun commentThumbCheck(wendaId: String) {
        if (!isLoginIntercept(false)) {
            return
        }
        mViewModel.commentThumbCheck(wendaId).observe(this, {
            if (it.success && it.data != 0) {
                mBinding.tvThumb.tag = true
                CommonViewUtils.setThumbStyle(mBinding.tvThumb, true)
            }
        })
    }

    /**
     * 点赞某个回答
     */
    @SuppressLint("SetTextI18n")
    private fun commentThumb() {
        if (!isLoginIntercept(true)) {
            return
        }
        mViewModel.commentThumb(answerBean!!._id).observe(this, {
            if (it.success) {
                mBinding.tvThumb.tag = true
                mBinding.tvThumb.text = (answerBean!!.thumbUp + 1).toString()
                CommonViewUtils.setThumbStyle(mBinding.tvThumb, true)
            }
        })
    }

    private fun commentPrise(count: Int) {
        mViewModel.commentPrise(answerBean!!._id, count, false).observe(this, {
            if (it.success) {
                toast("谢谢老板打赏!!!")
            }
        })
    }

    private fun showPriseDialog() {
        if (!isLoginIntercept(true) && answerBean != null) {
            return
        }
        val dialogBinding = CommonPriseDialogBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(dialogBinding.root)
        bottomSheetDialog.show()

        dialogBinding.ivAvatar.loadAvatar(false, answerBean!!.avatar)
        dialogBinding.tvNickname.text = answerBean!!.nickname
        dialogBinding.ivClose.setOnClickListener { bottomSheetDialog.dismiss() }

        dialogBinding.rvSob.layoutManager = GridLayoutManager(this, 3)
        val adapter = CommonPriseAdapter()
        dialogBinding.rvSob.adapter = adapter
        var selectItem: PriseSobBean? = null
        adapter.setOnItemClickListener { _, _, position ->
            adapter.data.forEach {
                selectItem = (adapter.getItem(position) as PriseSobBean)
                it.isChecked = selectItem == it
            }
            adapter.notifyDataSetChanged()
        }
        dialogBinding.btnPrise.setOnClickListener {
            selectItem?.let { commentPrise(it.value) }
            bottomSheetDialog.dismiss()
        }
    }


    private fun showReplyDialog(subComment: SubCommentBean? = null) {
        if (!isLoginIntercept(true)) {
            return
        }
        replyDialog.show()
        if (subComment == null) {
            answerBean?.let {
                replyDialog.setReplyTitle("评论 @${answerBean!!.nickname}")
            }
        } else {
            replyDialog.setReplyTitle("评论 @${subComment.beNickname}")
        }
        replyDialog.sendListener(object : ReplyBottomSheetDialog.OnSendListener {
            override fun onSend(v: String) {
                if (subComment == null) {
                    replyAnswer(v)
                } else {
                    replyComment(v, subComment)
                }
                replyDialog.dismiss()
            }
        })
    }

    private fun replyAnswer(str: String) {
        if (!isLoginIntercept(true)) {
            return
        }
        answerBean?.let {
            mViewModel.replyAnswer(
                WendaSubCommentInputBean(
                    parentId = it._id,
                    beNickname = it.nickname,
                    beUid = it.uid,
                    wendaId = it.wendaId,
                    content = str
                )
            ).observe(this,{res->
                if (res.success) {
                    toast("评论成功")
                    EventBusUtils.postEvent(UpdateItemEvent(UpdateItemEvent.Event.UPDATE_WENDA, answerBean!!.wendaId))
                }
            })
        }
    }

    private fun replyComment(str: String, subComment: SubCommentBean) {
        if (!isLoginIntercept(true)) {
            return
        }
        mViewModel.replyAnswer(
            WendaSubCommentInputBean(
                parentId = subComment.parentId!!,
                beNickname = subComment.beNickname!!,
                beUid = subComment.beUid,
                wendaId = answerBean!!.wendaId,
                content = str
            )
        ).observe(this,{res->
            if (res.success) {
                toast("评论成功")
//                EventBusUtils.postEvent(UpdateItemEvent(UpdateItemEvent.Event.UPDATE_WENDA, answerBean!!.wendaId))
            }
        })
    }
}