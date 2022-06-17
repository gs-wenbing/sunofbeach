package com.zwb.sob_moyu.activity

import android.graphics.Typeface
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
import com.zwb.lib_base.utils.*
import com.zwb.lib_common.R
import com.zwb.lib_common.adapter.ImageAdapter
import com.zwb.lib_common.bean.MoyuItemBean
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.event.UpdateItemEvent
import com.zwb.lib_common.service.home.wrap.HomeServiceWrap
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.lib_common.view.ReplyBottomSheetDialog
import com.zwb.sob_moyu.MoyuApi
import com.zwb.sob_moyu.MoyuViewModel
import com.zwb.sob_moyu.adapter.CommentAdapter
import com.zwb.sob_moyu.bean.MomentCommentBean
import com.zwb.sob_moyu.bean.MomentCommentInputBean
import com.zwb.sob_moyu.bean.SubCommentInputBean
import com.zwb.sob_moyu.databinding.MoyuActivityDetailBinding
import com.zwb.sob_moyu.databinding.MoyuDetailHeaderBinding

@Route(path = RoutePath.Moyu.PAGE_DETAIL)
class MoyuDetailActivity :
    BaseActivity<MoyuActivityDetailBinding, MoyuViewModel>() {

    override val mViewModel by viewModels<MoyuViewModel>()

    @Autowired(name = RoutePath.Moyu.PARAMS_MOYU_ID)
    lateinit var moyuId: String

    private var moyuInfo: MoyuItemBean? = null

    private var mCurrentPage = 1

    private lateinit var mAdapter: CommentAdapter

    private lateinit var headerBinding: MoyuDetailHeaderBinding

    private lateinit var replyDialog: ReplyBottomSheetDialog

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

        replyDialog = ReplyBottomSheetDialog(this@MoyuDetailActivity)

        setDefaultLoad(this.rvContent, MoyuApi.MOYU_DETAIL_URL)
        getMoyuDetail()
        initListener()
    }

    override fun setStatusBar() {
        super.setStatusBar()
        StatusBarUtil.setPaddingSmart(this, mBinding.includeBar.toolbar)
        StatusBarUtil.setPaddingSmart(this, mBinding.layoutHeaderAvatar)
    }

    private fun getMoyuDetail() {
        mViewModel.moyuDetail(moyuId, MoyuApi.MOYU_DETAIL_URL).observe(this, {
            if (it != null) {
                setViewData(it)
            }
        })
    }

    private fun setViewData(moyuInfo: MoyuItemBean) {
        this.moyuInfo = moyuInfo
        mBinding.tvHeaderNickname.text = moyuInfo.nickname
        mBinding.ivHeaderAvatar.loadAvatar(moyuInfo.vip, moyuInfo.avatar)

        var desc = if (TextUtils.isEmpty(moyuInfo.position)) "" else moyuInfo.position
        desc = if (TextUtils.isEmpty(moyuInfo.company)) desc else "${desc}@${moyuInfo.company}"
        headerBinding.tvHeader.setLeftString(desc)
        headerBinding.tvHeader.setRightString(DateUtils.timeFormat(moyuInfo.createTime))

        // 内容
        CommonViewUtils.setHtml(headerBinding.tvContent, moyuInfo.content)

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
        headerBinding.tvDetailStar.text = moyuInfo.thumbUpList.size.toString()
        headerBinding.tvReply.text = moyuInfo.commentCount.toString()
        setThumbStyle(moyuInfo.hasThumbUp)
    }

    /**
     * 设置点赞后的样式
     */
    private fun setThumbStyle(hasThumbUp:Boolean){
        // 已经点赞后 标记tvDetailStar的tag为true
        headerBinding.tvDetailStar.tag = hasThumbUp
        CommonViewUtils.setThumbStyle(headerBinding.tvDetailStar,hasThumbUp)
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
        val adapter = ImageAdapter(width, pics.toMutableList())
        rvPic.adapter = adapter
        adapter.setOnItemClickListener { _, _, position ->
            CommonViewUtils.showBigImage(rvPic, R.id.iv_image, pics, position)
        }
    }

    private fun initListener() {
        mBinding.includeBar.ivBack.setOnClickListener { finish() }

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as MomentCommentBean
            if (view.id == R.id.tv_comment_nickname || view.id == R.id.iv_comment_avatar) {
                UcenterServiceWrap.instance.launchDetail(item.userId)
            }else if(view.id == R.id.iv_comment_reply){
                showReplyDialog(item)
            }
        }
        mBinding.tvHeaderFollow.setOnClickListener {
            if (isLoginIntercept(true) && moyuInfo != null) {
                follow()
            }
        }
        headerBinding.tvDetailStar.setOnClickListener {
            if (isLoginIntercept(true) && it.tag == null || it.tag == false) {
                moyuThumb()
            }
        }

        headerBinding.tvReply.setOnClickListener {
            showReplyDialog()
        }

        headerBinding.tvShare.setOnClickListener {
            toast("分享===开发中...")
        }

        headerBinding.tvLink.setOnClickListener {
            moyuInfo?.linkUrl?.let {
                CommonViewUtils.toWebView(it)
            }
        }
    }

    private fun loadListData(page: Int) {
        mViewModel.getCommentList(moyuId, page, MoyuApi.COMMENT_URL).observe(this, {
            setCommentData(it)
        })
    }

    private fun setCommentData(listData: ListData<MomentCommentBean>?) {
        if (listData == null || listData.list.isNullOrEmpty()) {
            mAdapter.loadMoreEnd()
        } else {
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
            } else {
                mAdapter.loadMoreComplete()
            }
        }
    }

    override fun initObserve() {

    }

    override fun initRequestData() {
        loadListData(mCurrentPage)
    }

    /**
     * 关注状态
     */
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
    private fun follow() {
        mViewModel.follow(moyuInfo!!.userId).observe(this, {
            getFollowState(moyuInfo!!.userId)
        })
    }

    /**
     * 点赞
     */
    private fun moyuThumb(){
        mViewModel.moyuThumb(moyuId).observe(this,{
            if(it.success){
                setThumbStyle(true)
                EventBusUtils.postEvent(UpdateItemEvent(UpdateItemEvent.Event.UPDATE_MOYU,moyuId))
                headerBinding.tvDetailStar.text = (moyuInfo!!.thumbUpList.size+1).toString()
            }
        })
    }
    /**
     * 评论动态
     */
    private fun commentMoyu(str: String) {
        if (!isLoginIntercept(true)) {
            return
        }
        mViewModel.comment(MomentCommentInputBean(momentId = moyuId, content = str))
            .observe(this, {
                if (it.success) {
                    toast("评论成功")
                    mCurrentPage = 1
                    mAdapter.data.clear()
                    initRequestData()
                }
            })
    }
    /**
     * 回复评论
     */
    private fun replyComment(comment: MomentCommentBean, str: String) {
        if (!isLoginIntercept(true)) {
            return
        }
        mViewModel.replyComment(
            SubCommentInputBean(
                momentId = moyuId,
                commentId = comment.id,
                targetUserId = comment.userId,
                content = str
            )
        ).observe(this, {
            if (it.success) {
                toast("回复成功")
                mCurrentPage = 1
                mAdapter.data.clear()
                initRequestData()
            }
        })
    }

    private fun showReplyDialog(momentComment: MomentCommentBean? = null){
        if (!isLoginIntercept(true)) {
            return
        }
        replyDialog.show()
        momentComment?.let { replyDialog.setReplyTitle("回复 @${momentComment.nickname}") }
        replyDialog.sendListener(object : ReplyBottomSheetDialog.OnSendListener {
            override fun onSend(v: String) {
                if (momentComment == null) {
                    // 评论文章
                    commentMoyu(v)
                } else {
                    // 回复评论
                    replyComment(momentComment, v)
                }
                replyDialog.dismiss()
            }
        })
    }

}