package com.zwb.sob_ucenter.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zwb.lib_common.base.BaseListFragment
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.sob_ucenter.UcenterApi
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.adapter.RankingSobAdapter
import com.zwb.sob_ucenter.bean.RankingSobBean
import com.zwb.sob_ucenter.databinding.UcenterFragmentListBinding

class UserRankingFragment :
    BaseListFragment<RankingSobBean, UcenterFragmentListBinding, UcenterViewModel>(),
    BaseListFragment.RecyclerListener {

    override val mViewModel by viewModels<UcenterViewModel>()

    private lateinit var mAdapter: RankingSobAdapter

    override fun UcenterFragmentListBinding.initView() {

        mAdapter = RankingSobAdapter(mutableListOf())
        this.rvList.setHasFixedSize(true)
        this.rvList.layoutManager = LinearLayoutManager(mContext)
        this.rvList.adapter = mAdapter
        init(mAdapter, this.rvList, this.refreshLayout, this@UserRankingFragment)
        setRefreshEnable(false)
        setEnableLoadMore(false)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as RankingSobBean
            UcenterServiceWrap.instance.launchDetail(item.userId)
        }
    }

    override fun loadKey(): String {
        return UcenterApi.RANKING_SOB_URL
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        mViewModel.rankingSob(loadKey()).observe(viewLifecycleOwner, {
            it?.let {
                loadCompleted(action, list = it)
            }
        })
    }
}