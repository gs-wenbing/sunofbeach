package com.zwb.sob_wenda.activity

import android.annotation.SuppressLint
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.DateUtils
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.bean.SubCommentBean
import com.zwb.lib_common.bean.TitleMultiBean
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.home.wrap.HomeServiceWrap
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.sob_wenda.R
import com.zwb.sob_wenda.WendaViewModel
import com.zwb.sob_wenda.adapter.WendaAnswerAdapter
import com.zwb.sob_wenda.bean.AnswerBean
import com.zwb.sob_wenda.bean.WendaContentBean
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

    private lateinit var contentBinding:WendaDetailHeaderBinding

    private lateinit var mAdapter: WendaAnswerAdapter

    override fun WendaActivityAnswerBinding.initView() {
        mBinding.includeBar.tvSearch.gone()
        mBinding.includeBar.ivBack.setOnClickListener { finish() }

        contentBinding = WendaDetailHeaderBinding.inflate(layoutInflater)
        contentBinding.tvLabels.gone()
        contentBinding.layoutVisibility.visible()

        mAdapter = WendaAnswerAdapter( mutableListOf())
        this.rvContent.setHasFixedSize(true)
        this.rvContent.adapter = mAdapter
        this.rvContent.layoutManager = LinearLayoutManager(this@WendaAnswerActivity)
        mAdapter.addHeaderView(contentBinding.root)

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position)
            if(item is  SubCommentBean){
                when (view.id) {
                    R.id.tv_your_nickname, R.id.iv_your_avatar -> {
                        UcenterServiceWrap.instance.launchDetail(item.yourUid)
                    }
                    R.id.tv_be_nickname -> {
                        UcenterServiceWrap.instance.launchDetail(item.beUid)
                    }
                }
            }

        }
        mBinding.tvHeaderFollow.setOnClickListener {
            if(isLoginIntercept(true) && answerBean != null){
                follow(answerBean!!.uid)
            }
        }
        mBinding.ivHeaderAvatar.setOnClickListener {
            if(answerBean!=null){
                UcenterServiceWrap.instance.launchDetail(answerBean!!.uid)
            }
        }
    }

    override fun setStatusBar() {
        super.setStatusBar()
        StatusBarUtil.setPaddingSmart(this, mBinding.includeBar.toolbar)
        StatusBarUtil.setPaddingSmart(this, mBinding.layoutHeaderAvatar)
    }
    override fun initObserve() {
    }

    @SuppressLint("SetTextI18n")
    override fun initRequestData() {
        answerBean?.let {
            mBinding.tvHeaderNickname.text = it.nickname
            mBinding.ivHeaderAvatar.loadAvatar(it.isVip,it.avatar)
            contentBinding.codeView.showCode(it.content)
            contentBinding.codeView.webViewClient = object : WebViewClient() {
                override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
                ): Boolean {
                    if(request.url.toString().startsWith(Constants.WEBSITE_PREFIX)){
                        val arr = request.url.toString().split("/")
                        HomeServiceWrap.instance.launchDetail(arr[arr.size-1],"")
                        return true
                    }
                    return true
                }
            }
            mAdapter.addData(TitleMultiBean("评论（ ${it.wendaSubComments.size}）"))
            mAdapter.addData(it.wendaSubComments)
            getFollowState(it.uid)
        }
        wendaContent?.let {
            if(it.isResolve=="1"){
                val title =  "【已解决】${it.title}"
                contentBinding.tvTitle.text = UIUtils.setTextViewContentStyle(
                    title,
                    AbsoluteSizeSpan(UIUtils.dp2px(14f)),
                    ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorSuccess)),
                    0, 5
                )
            }else{
                contentBinding.tvTitle.text = it.title
            }
            contentBinding.tvStar.text = "${it.thumbUp} 好问题"
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

    private fun getFollowState(userId : String){
        mViewModel.followState(userId).observe(this,{
            mBinding.tvHeaderFollow.visible()
            if(it.success && it.data!=null){
                CommonViewUtils.setFollowState(mBinding.tvHeaderFollow,it.data!!)
            }
        })
    }


    /**
     * 关注
     */
    private fun follow(userId : String){
        mViewModel.follow(userId).observe(this, {
            getFollowState(userId)
        })
    }
}