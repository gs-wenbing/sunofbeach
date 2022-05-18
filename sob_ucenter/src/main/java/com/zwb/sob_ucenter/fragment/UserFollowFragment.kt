package com.zwb.sob_ucenter.fragment

import android.text.TextUtils
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zwb.lib_common.base.BaseListFragment
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.Constants.Ucenter.PAGE_FOLLOW
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.sob_ucenter.UcenterApi
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.adapter.FollowAdapter
import com.zwb.sob_ucenter.bean.FollowBean
import com.zwb.sob_ucenter.databinding.UcenterFragmentListBinding

class UserFollowFragment :
    BaseListFragment<FollowBean, UcenterFragmentListBinding, UcenterViewModel>(),
    BaseListFragment.RecyclerListener {

    override val mViewModel by viewModels<UcenterViewModel>()

    private lateinit var mAdapter: FollowAdapter

    private var userId: String? = null

    var pageType: Int = PAGE_FOLLOW

    override fun UcenterFragmentListBinding.initView() {
        userId = requireArguments().getString("userId")
        pageType = requireArguments().getInt(Constants.Ucenter.PAGE_TYPE)

        mAdapter = FollowAdapter(mutableListOf())
        this.rvList.setHasFixedSize(true)
        this.rvList.layoutManager = LinearLayoutManager(mContext)
        this.rvList.adapter = mAdapter
        init(mAdapter, this.rvList, this.refreshLayout, this@UserFollowFragment)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as FollowBean
            UcenterServiceWrap.instance.launchDetail(item.userId)
        }
    }

    override fun loadKey(): String {
        return if(pageType == PAGE_FOLLOW) UcenterApi.FOLLOW_LIST_URL else UcenterApi.FANS_LIST_URL
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        if(!TextUtils.isEmpty(userId)){
            mViewModel.followList(pageType, userId!!, page, loadKey()).observe(viewLifecycleOwner, {
                it?.let {
                    loadCompleted(action, list = it.list, pageSize = it.pageSize)
                }
            })
        } else {
            loadCompleted(action, list = null)
        }
    }
}