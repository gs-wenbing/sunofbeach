package com.zwb.sob_home.activity

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.text.TextUtils
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.DateUtils
import com.zwb.lib_base.utils.KeyBoardUtils
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.CommonApi
import com.zwb.lib_common.bean.ArticleTitleBean
import com.zwb.lib_common.bean.CollectInputBean
import com.zwb.lib_common.bean.CollectionBean
import com.zwb.lib_common.bean.TitleMultiBean
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.Constants.WEBSITE_URL
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.home.wrap.HomeServiceWrap
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.lib_common.view.FixedHeightBottomSheetDialog
import com.zwb.sob_home.HomeApi
import com.zwb.sob_home.HomeViewModel
import com.zwb.sob_home.R
import com.zwb.sob_home.adapter.CollectFolderAdapter
import com.zwb.sob_home.adapter.HomeDetailAdapter
import com.zwb.sob_home.bean.*
import com.zwb.sob_home.databinding.*


@Route(path = RoutePath.Home.PAGE_ARTICLE)
class ArticleDetailActivity : BaseActivity<HomeActivityArticleDetailBinding, HomeViewModel>() {

    override val mViewModel by viewModels<HomeViewModel>()

    @Autowired
    lateinit var articleId: String

    @Autowired
    lateinit var articleTitle: String

    private var article: ArticleDetailBean? = null

    private lateinit var contentBinding: HomeDetailContentLayoutBinding

    private lateinit var mAdapter: HomeDetailAdapter

    private lateinit var bottomSheetDialog: FixedHeightBottomSheetDialog

    //记录当前RecycleView 滚动的距离
    private var scrollY = 0

    private var switchScrollY = 0

    override fun HomeActivityArticleDetailBinding.initView() {
        Log.e("articleId=====", articleId)
        mBinding.includeBar.tvTitle.text = articleTitle
        mBinding.includeBar.tvSearch.gone()
        mBinding.includeBar.ivBack.setOnClickListener { finish() }
        mBinding.ivSwitch.tag = 0
        mBinding.tvReplyNum.gone()
        setDefaultLoad(this.refreshLayout, HomeApi.ARTICLE_DETAIL_URL)
        contentBinding = HomeDetailContentLayoutBinding.inflate(layoutInflater)
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
        mAdapter = HomeDetailAdapter(this@ArticleDetailActivity, mutableListOf())
        this.rvContent.setHasFixedSize(true)
        this.rvContent.adapter = mAdapter
        this.rvContent.layoutManager = LinearLayoutManager(this@ArticleDetailActivity)
        mAdapter.addHeaderView(contentBinding.root)

        bottomSheetDialog = FixedHeightBottomSheetDialog(
            this@ArticleDetailActivity,
            R.style.BottomSheetDialog,
            UIUtils.getScreenHeight() * 2 / 3
        )

        mBinding.ivHeaderAvatar.setOnClickListener {
            article?.let {
                UcenterServiceWrap.instance.launchDetail(article!!.userId)
            }
        }

        mBinding.tvHeaderFollow.setOnClickListener {
            if (isLoginIntercept(true) && article != null) {
                follow()
            }
        }

        mBinding.tvReply.setOnClickListener {
            showReplyDialog()
        }
        mBinding.ivList.setOnClickListener {
            showContentList()
        }

        mBinding.ivSwitch.setOnClickListener {
            if (mBinding.ivSwitch.tag == 1) {
                mBinding.ivSwitch.setImageResource(R.mipmap.ic_detail_reply)
//                this.rvContent.scrollBy(0, -10000)
                this.rvContent.scrollToPosition(0)
                mBinding.ivSwitch.tag = 0
            } else {
                switchScrollY = scrollY
                mBinding.ivSwitch.setImageResource(R.mipmap.ic_detail_back)
                this.rvContent.scrollToPosition(2)
                mBinding.ivSwitch.tag = 1
            }
        }

        mBinding.ivStar.setOnClickListener {
            if (mBinding.tvStarNum.tag == true) {
                return@setOnClickListener
            }
            thumbUp()
        }

        mBinding.ivCollection.setOnClickListener {
            if (TextUtils.isEmpty(articleId) || TextUtils.isEmpty(articleTitle)) {
                return@setOnClickListener
            }
            if (mBinding.ivCollection.tag is String && !TextUtils.isEmpty(mBinding.ivCollection.tag.toString())) {
                unFavorite(mBinding.ivCollection.tag.toString())
                return@setOnClickListener
            }
            getCollectFolder()
        }
        mBinding.rvContent.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                scrollY += dy
                // 标题栏显示头像，隐藏标题
                if (scrollY >= contentBinding.layoutHeader.height) {
                    mBinding.layoutHeaderAvatar.visible()
                    mBinding.includeBar.tvTitle.gone()
                    mBinding.layoutHeaderAvatar.alpha =
                        1f * (scrollY - contentBinding.layoutHeader.height) / contentBinding.layoutHeader.height
                } else {
                    mBinding.layoutHeaderAvatar.gone()
                    mBinding.includeBar.tvTitle.visible()
                    mBinding.includeBar.tvTitle.alpha =
                        1f * (contentBinding.layoutHeader.height - scrollY) / contentBinding.layoutHeader.height
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                when (newState) {
                    RecyclerView.SCROLL_STATE_IDLE -> {
                        //处于静止状态时，继续加载图片
                        if (!this@ArticleDetailActivity.isDestroyed && !this@ArticleDetailActivity.isFinishing) {
                            Glide.with(this@ArticleDetailActivity).resumeRequests()
                        }
                    }
                    RecyclerView.SCROLL_STATE_DRAGGING,
                    RecyclerView.SCROLL_STATE_SETTLING -> {
                        //处于滑动状态时，停止加载图片，保证操作界面流畅
                        if (!this@ArticleDetailActivity.isDestroyed && !this@ArticleDetailActivity.isFinishing) {
                            Glide.with(this@ArticleDetailActivity).pauseRequests()
                        }
                    }
                }
                super.onScrollStateChanged(recyclerView, newState)

            }
        })

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.tv_related_nickname, R.id.iv_related_avatar -> {
                    val recommend = adapter.getItem(position) as ArticleRecommendBean
                    UcenterServiceWrap.instance.launchDetail(recommend.userId)
                }
                R.id.tv_comment_nickname, R.id.iv_comment_avatar -> {
                    val comment = adapter.getItem(position) as CommentBean
                    UcenterServiceWrap.instance.launchDetail(comment.userId)
                }
            }
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            if (adapter.getItem(position) is ArticleRecommendBean) {
                val item = adapter.getItem(position) as ArticleRecommendBean
                HomeServiceWrap.instance.launchDetail(item.id, item.title)
            }
        }
    }

    override fun initObserve() {
    }

    override fun setStatusBar() {
        super.setStatusBar()
        StatusBarUtil.setPaddingSmart(this, mBinding.includeBar.toolbar)
        StatusBarUtil.setPaddingSmart(this, mBinding.layoutHeaderAvatar)
        mBinding.layoutHeaderAvatar.gone()
    }

    override fun initRequestData() {
        // 文章详情
        mViewModel.getArticleDetail(articleId, HomeApi.ARTICLE_DETAIL_URL).observe(this, {
            it?.let {
                article = it
                setContentData(it)
                getPriseQrCode(it.userId)
                getFollowState(it.userId)
            }
        })
        getArticleCommentList()
        if (!isLoginIntercept(false)) {
            return
        }
        checkArticleThumbUp()
        checkCollected()
    }

    /**
     * 获取打赏二维码
     */
    private fun getPriseQrCode(userId: String) {
        mViewModel.getPriseQrCode(userId).observe(this, {
            if (it.success && it.data != null) {
                contentBinding.ivQrcUrl.visible()
                val code = it.data
                Glide.with(this)
                    .load(code!!.qrcUrl)
                    .placeholder(R.drawable.shape_grey_background)
                    .override(UIUtils.dp2px(100f), UIUtils.dp2px(100f))
                    .thumbnail(0.1f)
                    .into(contentBinding.ivQrcUrl)
                contentBinding.tvPriseTips.text = code.tips
            } else {
                contentBinding.ivQrcUrl.gone()
            }
        })
    }

    /**
     * 文章评论列表
     */
    private fun getArticleCommentList() {
        mViewModel.getArticleCommentList(articleId, 1, HomeApi.ARTICLE_COMMENT_URL).observe(this, {
            mAdapter.addData(TitleMultiBean("文章评论"))
            it.forEach { comment ->
                mAdapter.addData(comment)
                mAdapter.addData(comment.subComments)

            }
            mBinding.tvReplyNum.visible()
            mBinding.tvReplyNum.text = it.size.toString()
            getArticleRecommend()
        })
    }

    /**
     * 获取相关推荐
     */
    private fun getArticleRecommend() {
        mViewModel.getArticleRecommend(articleId, HomeApi.ARTICLE_RECOMMEND_URL).observe(this, {
            it?.let {
                mAdapter.addData(TitleMultiBean("相关推荐"))
                mAdapter.addData(it)
            }
        })
    }

    /**
     * 检测是否点赞
     */
    private fun checkArticleThumbUp() {
        mViewModel.checkArticleThumbUp(articleId).observe(this, {
            if (it != null && it.success) {
                setStarStyle()
            }
        })
    }

    /**
     * 点赞
     */
    private fun thumbUp() {
        if (!isLoginIntercept(true)) {
            return
        }
        mViewModel.articleThumbUp(articleId).observe(this@ArticleDetailActivity, {
            if (it.success) {
                mBinding.tvStarNum.text = it.data.toString()
                setStarStyle()
            }
        })
    }

    /**
     * 检测是否收藏
     */
    private fun checkCollected() {
        mViewModel.checkCollected(WEBSITE_URL + articleId).observe(this, {
            if (it.success && !TextUtils.isEmpty(it.data) && it.data != "0") {
                setCollectStyle(true, it.data.toString())
            } else {
                setCollectStyle(false)
            }
        })
    }

    /**
     * 获取收藏文件夹
     */
    private fun getCollectFolder() {
        if (!isLoginIntercept(true)) {
            return
        }
        val adapter = showCollectionFolder()
        mViewModel.collectionList(1, CommonApi.COLLECTION_LIST_URL)
            .observe(this@ArticleDetailActivity, {
                adapter?.setNewData(it)
            })
    }

    /**
     * 收藏
     */
    private fun favorite(body: CollectInputBean) {
        if (!isLoginIntercept(true)) {
            return
        }
        mViewModel.favorite(body).observe(this, {
            checkCollected()
        })
    }

    /**
     * 取消收藏
     */
    private fun unFavorite(favoriteId: String) {
        if (!isLoginIntercept(true)) {
            return
        }
        mViewModel.unFavorite(favoriteId).observe(this, {
            checkCollected()
        })
    }

    /**
     * 评论
     */
    private fun sendReply(str: String) {
        if (!isLoginIntercept(true)) {
            return
        }
        toast("评论内容：$str")
//        mViewModel.commentArticle(CommentInputBean(articleId = articleId, commentContent = str))
//            .observe(this, {
//                if (it.success) {
//                    toast("评论成功")
//                }
//            })
    }

    /**
     * 关注状态
     */
    private fun getFollowState(userId: String) {
        mViewModel.followState(userId).observe(this, {
            mBinding.tvHeaderFollow.visible()
            if (it.success && it.data != null) {
                CommonViewUtils.setFollowState(contentBinding.tvFollow, it.data!!)
                CommonViewUtils.setFollowState(mBinding.tvHeaderFollow, it.data!!)
            }
        })
    }

    /**
     * 关注
     */
    private fun follow() {
        mViewModel.follow(article!!.userId).observe(this, {
            getFollowState(article!!.userId)
        })
    }

    /**
     * 设置文章数据
     */
    @SuppressLint("SetTextI18n")
    private fun setContentData(article: ArticleDetailBean) {
        articleTitle = article.title
        mBinding.includeBar.tvTitle.text = articleTitle

        //这里的CODE 为需要显示的代码，类型为String，使用的时候自己替换下。
        contentBinding.codeView.showCode(article.content)

        contentBinding.ivAvatar.loadAvatar(article.isVip == 1, article.avatar)

        contentBinding.tvNickname.text = article.nickname

        contentBinding.tvFollow.setOnClickListener {
            if (isLoginIntercept(true)) {
                follow()
            }
        }
        contentBinding.ivAvatar.setOnClickListener {
            UcenterServiceWrap.instance.launchDetail(article.userId)
        }
        contentBinding.tvPublishTime.text = "发表于 ${DateUtils.timeFormat(article.createTime)}"
        contentBinding.tvViewCount.text = "热度：${article.viewCount}"
        contentBinding.tvLabels.text = article.labels.toString()
        mBinding.tvHeaderNickname.text = article.nickname
        mBinding.ivHeaderAvatar.loadAvatar(article.isVip == 1, article.avatar)
        mBinding.tvStarNum.text = article.thumbUp.toString()
    }

    /**
     * 设置点赞图标的颜色
     */
    private fun setStarStyle() {
        mBinding.tvStarNum.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        mBinding.tvStarNum.tag = true
        mBinding.ivStar.setImageDrawable(
            UIUtils.tintDrawable(
                mBinding.ivStar.drawable,
                ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorAccent))
            )
        )
    }

    /**
     * 设置收藏图标的颜色
     */
    private fun setCollectStyle(isCollect: Boolean, favoriteId: String = "") {
        mBinding.ivCollection.tag = favoriteId
        mBinding.ivCollection.setImageDrawable(
            UIUtils.tintDrawable(
                mBinding.ivCollection.drawable,
                ColorStateList.valueOf(
                    ContextCompat.getColor(
                        this,
                        if (isCollect) R.color.colorAccent else R.color.icon_color
                    )
                )
            )
        )
    }

    /**
     * 显示收藏夹列表
     */
    private fun showCollectionFolder(): CollectFolderAdapter? {
        if (!isLoginIntercept(true)) {
            return null
        }
        val dialogBinding = HomeDialogCollectionFolderBinding.inflate(layoutInflater)
        dialogBinding.rvFolder.layoutManager = LinearLayoutManager(this)
        val adapter = CollectFolderAdapter(mutableListOf())
        dialogBinding.rvFolder.adapter = adapter
        adapter.setOnItemChildClickListener { _, view, position ->
            if (view.id == R.id.btn_collect) {
                bottomSheetDialog.dismiss()
                val folder = adapter.getItem(position) as CollectionBean
                favorite(
                    CollectInputBean(
                        collectionId = folder._id,
                        title = articleTitle,
                        url = WEBSITE_URL + articleId
                    )
                )
            }
        }

        setDefaultLoad(dialogBinding.rvFolder, CommonApi.COLLECTION_LIST_URL)
        bottomSheetDialog.setContentView(dialogBinding.root)
        bottomSheetDialog.show()
        return adapter
    }

    /**
     * 显示目录
     */
    private fun showContentList() {
        val titleList = contentBinding.codeView.getTitleList()
        Log.e("titleList=====", titleList.toString())
        val dialogBinding = HomeDialogContentListBinding.inflate(layoutInflater)
        dialogBinding.ivClose.setOnClickListener { bottomSheetDialog.dismiss() }
        dialogBinding.rvList.layoutManager = LinearLayoutManager(this)
        val adapter = object : BaseQuickAdapter<ArticleTitleBean, BaseViewHolder>(
            R.layout.home_dialog_adapter_content_list,
            titleList
        ) {
            override fun convert(helper: BaseViewHolder, item: ArticleTitleBean?) {
                item?.let {
                    val tvTitle = helper.getView<TextView>(R.id.tv_title)
                    tvTitle.text = item.title
                    when (it.level) {
                        2 -> tvTitle.setPadding(UIUtils.dp2px(8f), 0, 0, 0)
                        3 -> {
                            tvTitle.setPadding(UIUtils.dp2px(28f), 0, 0, 0)
                            tvTitle.textSize = 13f
                        }
                        4 -> {
                            tvTitle.setPadding(UIUtils.dp2px(48f), 0, 0, 0)
                            tvTitle.textSize = 12f
                        }
                    }
                }
            }
        }

        dialogBinding.rvList.adapter = adapter
        adapter.setOnItemClickListener { _, _, position ->
            val item = adapter.getItem(position) as ArticleTitleBean
            val id = item.elementId + ""
            val js = "javascript:getElementTop('$id')"
            contentBinding.codeView.evaluateJavascript(js) { value ->
                if (value != null) {
                    // 从js中获取到offsetTop 要dp2px转一下，不知道为啥？？？
                    mBinding.rvContent.scrollBy(0, UIUtils.dp2px(value.toFloat()) - scrollY)
                }
            }

            bottomSheetDialog.dismiss()
        }
        bottomSheetDialog.setContentView(dialogBinding.root)
        bottomSheetDialog.show()
    }

    private fun showReplyDialog() {
        if (!isLoginIntercept(true)) {
            return
        }
        val dialogBinding = HomeDialogReplyBinding.inflate(layoutInflater)
        bottomSheetDialog.setContentView(dialogBinding.root)
        bottomSheetDialog.show()

        dialogBinding.editReply.isFocusable = true
        dialogBinding.editReply.isFocusableInTouchMode = true
        dialogBinding.editReply.requestFocus()
        dialogBinding.btnSend.setOnClickListener {
            if (!TextUtils.isEmpty(dialogBinding.editReply.text)) {
                sendReply(dialogBinding.editReply.text.toString())
            }
        }
        dialogBinding.editReply.post {
            KeyBoardUtils.showInput(this, dialogBinding.editReply)
        }
    }

}