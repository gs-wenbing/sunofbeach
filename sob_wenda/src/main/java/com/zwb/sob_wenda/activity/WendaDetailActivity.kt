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
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.DateUtils
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.bean.TitleMultiBean
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.home.wrap.HomeServiceWrap
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.lib_common.service.wenda.wrap.WendaServiceWrap
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.sob_wenda.R
import com.zwb.sob_wenda.WendaApi
import com.zwb.sob_wenda.WendaViewModel
import com.zwb.sob_wenda.adapter.WendaDetailAdapter
import com.zwb.sob_wenda.bean.AnswerBean
import com.zwb.sob_wenda.bean.WendaBean
import com.zwb.sob_wenda.bean.WendaContentBean
import com.zwb.sob_wenda.databinding.WendaActivityDetailBinding
import com.zwb.sob_wenda.databinding.WendaDetailHeaderBinding

@Route(path = RoutePath.Wenda.PAGE_DETAIL)
class WendaDetailActivity : BaseActivity<WendaActivityDetailBinding, WendaViewModel>() {

    override val mViewModel by viewModels<WendaViewModel>()

    @Autowired(name = RoutePath.Wenda.PARAMS_WENDA_ID)
    lateinit var wendaId: String

    private var wendaContent: WendaContentBean? = null

    private lateinit var contentBinding: WendaDetailHeaderBinding

    private lateinit var mAdapter: WendaDetailAdapter

    override fun WendaActivityDetailBinding.initView() {
        mBinding.includeBar.tvSearch.gone()
        mBinding.includeBar.ivBack.setOnClickListener { finish() }
        setDefaultLoad(this.refreshLayout, WendaApi.WENDA_DETAIL_URL)

        contentBinding = WendaDetailHeaderBinding.inflate(layoutInflater)

        mAdapter = WendaDetailAdapter(mutableListOf())
        this.rvContent.setHasFixedSize(true)
        this.rvContent.adapter = mAdapter
        this.rvContent.layoutManager = LinearLayoutManager(this@WendaDetailActivity)
        mAdapter.addHeaderView(contentBinding.root)

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.tv_comment_nickname || view.id == R.id.iv_comment_avatar) {
                when (val item = adapter.getItem(position)) {
                    is WendaBean -> {
                        UcenterServiceWrap.instance.launchDetail(item.userId)
                    }
                    is AnswerBean -> {
                        UcenterServiceWrap.instance.launchDetail(item.uid)
                    }
                }

            }
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            when (val item = adapter.getItem(position)) {
                is WendaBean -> {
                    WendaServiceWrap.instance.launchDetail(item.id)
                }
                is AnswerBean -> {
                    WendaServiceWrap.instance.launchAnswerDetail(wendaContent, item)
                }
            }
        }
        mBinding.tvHeaderFollow.setOnClickListener {
            if (isLoginIntercept(true) && wendaContent != null) {
                follow(wendaContent!!.userId)
            }
        }
        mBinding.ivHeaderAvatar.setOnClickListener {
            if (wendaContent != null) {
                UcenterServiceWrap.instance.launchDetail(wendaContent!!.userId)
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

    override fun initRequestData() {
        mViewModel.getWendaDetail(wendaId, WendaApi.WENDA_DETAIL_URL).observe(this, {
            it?.let {
                wendaContent = it
                setContentData(it)
                getFollowState(it.userId)
            }
        })
        mViewModel.getWendaAnswerList(wendaId, 1, WendaApi.WENDA_ANSWER_LIST_URL).observe(this, {
            if (it != null && it.isNotEmpty()) {
                mAdapter.addData(TitleMultiBean("回答 （${it.size}）"))
                mAdapter.addData(it)
            }
            getWendaRelative()
        })
    }

    private fun getWendaRelative() {
        mViewModel.getWendaRelative(wendaId, WendaApi.WENDA_RELATIVE_URL).observe(this, {
            it?.let {
                mAdapter.addData(TitleMultiBean("相关问题"))
                mAdapter.addData(it)
            }
        })
    }

    private fun getFollowState(userId: String) {
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
     * 设置数据
     */
    @SuppressLint("JavascriptInterface", "SetTextI18n")
    private fun setContentData(wenda: WendaContentBean) {
        if (wenda.isResolve == "1") {
            val title = "【已解决】${wenda.title}"
            contentBinding.tvTitle.text = UIUtils.setTextViewContentStyle(
                title,
                AbsoluteSizeSpan(UIUtils.dp2px(14f)),
                ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorSuccess)),
                0, 5
            )
        } else {
            contentBinding.tvTitle.text = wenda.title
        }
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
        //这里的CODE 为需要显示的代码，类型为String，使用的时候自己替换下。
        contentBinding.codeView.showCode(wenda.description)

        contentBinding.tvStar.text = "${wenda.thumbUp} 好问题"
        contentBinding.tvSob.text = wenda.sob.toString()
        contentBinding.tvView.text = "${wenda.viewCount} 浏览"
        contentBinding.tvPublishTime.text = "发表于 ${DateUtils.timeFormat(wenda.createTime)}"
        contentBinding.tvQuestioner.gone()

        contentBinding.tvLabels.text = wenda.labels.toString()

        mBinding.tvHeaderNickname.text = wenda.nickname
        mBinding.ivHeaderAvatar.loadAvatar(wenda.isVip=="1",wenda.avatar)
    }
}