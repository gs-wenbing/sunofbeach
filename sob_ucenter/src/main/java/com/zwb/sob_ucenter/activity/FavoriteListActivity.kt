package com.zwb.sob_ucenter.activity

import android.view.Gravity
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_common.base.BaseListActivity
import com.zwb.lib_common.bean.CollectionBean
import com.zwb.lib_common.constant.Constants.WEBSITE_URL
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.home.wrap.HomeServiceWrap
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.sob_ucenter.UcenterApi
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.adapter.FavoriteAdapter
import com.zwb.sob_ucenter.bean.FavoriteBean
import com.zwb.sob_ucenter.databinding.UcenterActivityFavoriteListBinding

@Route(path = RoutePath.Ucenter.PAGE_FAVORITE_LIST)
class FavoriteListActivity :
    BaseListActivity<FavoriteBean, UcenterActivityFavoriteListBinding, UcenterViewModel>(),
    BaseListActivity.RecyclerListener {

    override val mViewModel by viewModels<UcenterViewModel>()

    @JvmField
    @Autowired
    var collection: CollectionBean? = null


    private lateinit var mAdapter: FavoriteAdapter

    override fun UcenterActivityFavoriteListBinding.initView() {

        mBinding.includeBar.ivRight.gone()
        collection?.let {
            mBinding.includeBar.tvTitle.text = it.name
            mBinding.includeBar.tvTitle.gravity = Gravity.FILL
        }


        mAdapter = FavoriteAdapter(mutableListOf())
        this.rvList.setHasFixedSize(true)
        this.rvList.layoutManager = LinearLayoutManager(this@FavoriteListActivity)
        this.rvList.adapter = mAdapter
        init(mAdapter, this.rvList, this.refreshLayout, this@FavoriteListActivity)

        initListener()
    }

    override fun setStatusBar() {
        super.setStatusBar()
        StatusBarUtil.setPaddingSmart(this, mBinding.includeBar.toolbar)
    }

    private fun initListener() {
        mBinding.includeBar.ivBack.setOnClickListener { finish() }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as FavoriteBean
            CommonViewUtils.toWebView(item.url)
        }
    }

    override fun loadKey(): String {
        return UcenterApi.FAVORITE_LIST_URL
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        if (collection != null) {
            mViewModel.favoriteList(collection!!._id, page, loadKey()).observe(this, {
                it?.let {
                    loadCompleted(action, list = it.content, pageSize = it.size)
                }
            })
        } else {
            loadCompleted(action, list = null)
        }
    }
}