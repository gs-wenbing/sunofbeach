package com.zwb.sob_ucenter.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zwb.lib_common.base.BaseListFragment
import com.zwb.sob_ucenter.UcenterApi
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.adapter.MsgSystemAdapter
import com.zwb.sob_ucenter.bean.*
import com.zwb.sob_ucenter.databinding.UcenterFragmentListBinding

class UserMessageSystemFragment :
    BaseListFragment<MsgSystemBean, UcenterFragmentListBinding, UcenterViewModel>(),
    BaseListFragment.RecyclerListener {

    override val mViewModel by viewModels<UcenterViewModel>()

    private lateinit var mAdapter: MsgSystemAdapter

    override fun UcenterFragmentListBinding.initView() {

        mAdapter = MsgSystemAdapter(mutableListOf())
        this.rvList.setHasFixedSize(true)
        this.rvList.layoutManager = LinearLayoutManager(mContext)
        this.rvList.adapter = mAdapter
        init(mAdapter, this.rvList, this.refreshLayout, this@UserMessageSystemFragment)

        initListener()
    }

    private fun initListener() {
        mAdapter.setOnItemClickListener { adapter, view, position ->

        }
    }

    override fun loadKey(): String {
        return UcenterApi.MESSAGE_SYSTEM_URL
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        mViewModel.messageSystemList(page, loadKey()).observe(viewLifecycleOwner, {
            it?.let {
                loadCompleted(action, list = it.content)
            }
        })
    }

}
