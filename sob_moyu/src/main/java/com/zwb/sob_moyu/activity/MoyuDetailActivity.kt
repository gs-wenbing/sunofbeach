package com.zwb.sob_moyu.activity

import android.graphics.Typeface
import android.text.Html
import android.text.TextUtils
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_base.bean.ListData
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.DateUtils
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.R
import com.zwb.lib_common.adapter.ImageAdapter
import com.zwb.lib_common.bean.MoyuItemBean
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.lib_common.view.HtmlImageGetter
import com.zwb.sob_moyu.MoyuApi
import com.zwb.sob_moyu.MoyuViewModel
import com.zwb.sob_moyu.adapter.CommentAdapter
import com.zwb.sob_moyu.bean.MomentCommentBean
import com.zwb.sob_moyu.databinding.MoyuActivityDetailBinding
import com.zwb.sob_moyu.databinding.MoyuDetailHeaderBinding

@Route(path = RoutePath.Moyu.PAGE_DETAIL)
class MoyuDetailActivity :
    BaseActivity<MoyuActivityDetailBinding, MoyuViewModel>() {

    override val mViewModel by viewModels<MoyuViewModel>()

    @Autowired(name = RoutePath.Moyu.PARAMS_MOYU_ID)
    lateinit var moyuId: String

    private var mCurrentPage = 1

    private lateinit var mAdapter: CommentAdapter

    private lateinit var headerBinding: MoyuDetailHeaderBinding

    override fun MoyuActivityDetailBinding.initView() {
        mBinding.includeBar.tvSearch.gone()

        mAdapter = CommentAdapter(this@MoyuDetailActivity, mutableListOf())
        headerBinding = MoyuDetailHeaderBinding.inflate(layoutInflater)
        mAdapter.addHeaderView(headerBinding.root)
        this.rvContent.setHasFixedSize(true)
        this.rvContent.layoutManager = LinearLayoutManager(this@MoyuDetailActivity)
        this.rvContent.adapter = mAdapter
        //加载更多
        mAdapter.setOnLoadMoreListener({
            mCurrentPage++
            loadListData(mCurrentPage)
        }, this.rvContent)

        setDefaultLoad(this.rvContent,MoyuApi.MOYU_DETAIL_URL)

        getMoyuDetail()
        initListener()
    }

    override fun setStatusBar() {
        super.setStatusBar()
        StatusBarUtil.setPaddingSmart(this, mBinding.includeBar.toolbar)
        StatusBarUtil.setPaddingSmart(this, mBinding.layoutHeaderAvatar)
    }

    private fun getMoyuDetail(){
        mViewModel.moyuDetail(moyuId,MoyuApi.MOYU_DETAIL_URL).observe(this,{
            if(it != null){
                setViewData(it)
            }
        })
    }

    private fun setViewData(moyuInfo:MoyuItemBean) {
        mBinding.tvHeaderNickname.text = moyuInfo.nickname
        mBinding.ivHeaderAvatar.loadAvatar(moyuInfo.vip,moyuInfo.avatar)

        var desc = if (TextUtils.isEmpty(moyuInfo.position)) "" else moyuInfo.position
        desc = if (TextUtils.isEmpty(moyuInfo.company)) desc else "${desc}@${moyuInfo.company}"
        headerBinding.tvHeader.setLeftString(desc)
        headerBinding.tvHeader.setRightString(DateUtils.timeFormat(moyuInfo.createTime))

        // 内容
        CommonViewUtils.setHtml(headerBinding.tvContent,moyuInfo.content)

        // 链接
        if (!TextUtils.isEmpty(moyuInfo.linkTitle) && !TextUtils.isEmpty(moyuInfo.linkUrl)) {
            headerBinding.tvLink.visible()
            headerBinding.tvLink.setLeftTopString(moyuInfo.linkTitle)
            headerBinding.tvLink.setLeftString(moyuInfo.linkUrl)
            headerBinding.tvLink.leftTextView.setTypeface(null, Typeface.ITALIC)
        } else {
            headerBinding.tvLink.gone()
        }

        if (moyuInfo.images.isNotEmpty()) {
            headerBinding.rvPic.visible()
            setImageData(moyuInfo.images, headerBinding.rvPic)
        } else {
            headerBinding.rvPic.gone()
        }

        if (!TextUtils.isEmpty(moyuInfo.topicName)) {
            headerBinding.tvTopicName.visible()
            headerBinding.tvTopicName.text = moyuInfo.topicName
        } else {
            headerBinding.tvTopicName.gone()
        }
        headerBinding.tvStar.text = moyuInfo.thumbUpList.size.toString()
        headerBinding.tvReply.text = moyuInfo.commentCount.toString()
    }

    private fun setImageData(pics: List<String>, rvPic: RecyclerView) {
        var width = (UIUtils.getScreenWidth() - UIUtils.dp2px(44f)) / 2
        when {
            (pics.size in 1..2) -> {
                width = (UIUtils.getScreenWidth() - UIUtils.dp2px(44f)) / 2
                rvPic.layoutManager = GridLayoutManager(this, 2)
            }
            pics.size >= 3 -> {
                width = (UIUtils.getScreenWidth() - UIUtils.dp2px(44f)) / 3
                rvPic.layoutManager = GridLayoutManager(this, 3)
            }
        }
        rvPic.adapter = ImageAdapter(width, pics.toMutableList())
    }

    private fun initListener() {
        mBinding.includeBar.ivBack.setOnClickListener { finish() }

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as MomentCommentBean
            if (view.id == com.zwb.sob_moyu.R.id.tv_comment_nickname || view.id == R.id.iv_comment_avatar) {
                UcenterServiceWrap.instance.launchDetail(item.userId)
            }
        }

        headerBinding.tvStar.setOnClickListener {
            toast("点赞===开发中...")
        }

        headerBinding.tvReply.setOnClickListener {
            toast("评论===开发中...")
        }

        headerBinding.tvShare.setOnClickListener {
            toast("分享===开发中...")
        }

        headerBinding.tvLink.setOnClickListener {
            toast("链接===开发中...")
        }
    }

    private fun loadListData(page: Int) {
        mViewModel.getFollowList(moyuId, page, MoyuApi.COMMENT_URL).observe(this, {
            setCommentData(it)
        })
    }

    private fun setCommentData(listData: ListData<MomentCommentBean>?){
        if(listData==null || listData.list.isNullOrEmpty()){
            mAdapter.loadMoreEnd()
        }else{
            val list = mutableListOf<MultiItemEntity>()
            listData.list.forEach { item ->
                list.add(item)
                list.addAll(item.subComments)
            }
            //加载更多
            mAdapter.addData(list)
            if (list.size < listData.pageSize) {
                //加载的数据少于一页时
                mAdapter.loadMoreEnd()
            }else{
                mAdapter.loadMoreComplete()
            }
        }
    }

    override fun initObserve() {

    }

    override fun initRequestData() {
        loadListData(mCurrentPage)
    }
}