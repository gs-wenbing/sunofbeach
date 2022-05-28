package com.zwb.sob_ucenter.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.zwb.lib_common.base.BaseListFragment
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.home.wrap.HomeServiceWrap
import com.zwb.lib_common.service.wenda.wrap.WendaServiceWrap
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.sob_ucenter.R
import com.zwb.sob_ucenter.UcenterApi
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.adapter.UserCenterArticleAdapter
import com.zwb.sob_ucenter.bean.ArticleBean
import com.zwb.sob_ucenter.bean.ShareBean
import com.zwb.sob_ucenter.bean.UserWendaBean
import com.zwb.sob_ucenter.databinding.UcenterFragmentListBinding

class UserCenterArticleFragment :
    BaseListFragment<MultiItemEntity, UcenterFragmentListBinding, UcenterViewModel>(),
    BaseListFragment.RecyclerListener {
    override val mViewModel by viewModels<UcenterViewModel>()

    private lateinit var mAdapter: UserCenterArticleAdapter

    lateinit var userId: String

    private lateinit var dataType: String

    override fun UcenterFragmentListBinding.initView() {
        userId = requireArguments().getString("userId", "")
        dataType = requireArguments().getString(DATA_TYPE, DATA_ARTICLE)
        when(dataType){
            DATA_ARTICLE ->{
                mAdapter = UserCenterArticleAdapter(R.layout.ucenter_adapter_article, mutableListOf())
            }
            DATA_SHARE ->{
                mAdapter = UserCenterArticleAdapter(R.layout.ucenter_adapter_share, mutableListOf())
            }
            DATA_WENDA ->{
                mAdapter = UserCenterArticleAdapter(R.layout.ucenter_adapter_wenda, mutableListOf())
            }
        }
        this.rvList.layoutManager = LinearLayoutManager(mContext)
        this.rvList.adapter = mAdapter
        init(mAdapter, this.rvList, this.refreshLayout, this@UserCenterArticleFragment)

        mAdapter.setOnItemClickListener { adapter, view, position ->
            when {
                adapter.getItem(position) is ArticleBean -> {
                    val item = adapter.getItem(position) as ArticleBean
                    HomeServiceWrap.instance.launchDetail(item.id!! , item.title!!)
                }
                adapter.getItem(position) is ShareBean -> {
                    val item = adapter.getItem(position) as ShareBean
                    CommonViewUtils.toWebView(item.url)
                }
                adapter.getItem(position) is UserWendaBean -> {
                    val item = adapter.getItem(position) as UserWendaBean
                    WendaServiceWrap.instance.launchDetail(item.wendaComment.wendaId)
                }
            }
        }
    }

    override fun loadKey(): String {
        when(dataType){
            DATA_ARTICLE ->{
                return UcenterApi.USER_ARTICLE_LIST_URL
            }
            DATA_SHARE ->{
                return UcenterApi.USER_SHARE_LIST_URL
            }
            DATA_WENDA ->{
                return UcenterApi.USER_WENDA_LIST_URL
            }
        }
        return UcenterApi.USER_ARTICLE_LIST_URL
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        when(dataType){
            DATA_ARTICLE ->{
                getUserArticleList(action, page)
            }
            DATA_SHARE ->{
                getUserShareList(action, page)
            }
            DATA_WENDA ->{
                getUserWendaList(action, page)
            }
        }
    }

    private fun getUserArticleList(action: Int,  page: Int){
        mViewModel.getUserArticleList(userId, page, loadKey()).observe(viewLifecycleOwner, {
            it?.let {
                loadCompleted(action, list = it.list, pageSize = it.pageSize)
            }
        })
    }
    private fun getUserShareList(action: Int,  page: Int){
        mViewModel.getUserShareList(userId, page, loadKey()).observe(viewLifecycleOwner, {
            if(it==null){
                loadCompleted(action, null)
            }else{
                loadCompleted(action, list = it.list, pageSize = it.pageSize)
            }
        })
    }

    private fun getUserWendaList(action: Int, page: Int){
        mViewModel.getUserWendaList(userId, page, loadKey()).observe(viewLifecycleOwner, {
            if(it==null){
                loadCompleted(action, null)
            }else{
                loadCompleted(action, list = it.content, pageSize = it.size)
            }
        })
    }

    companion object{
        const val DATA_TYPE="data_type"
        const val DATA_ARTICLE="article"
        const val DATA_SHARE="share"
        const val DATA_WENDA="wenda"

    }

}