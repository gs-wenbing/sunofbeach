package com.zwb.sob_ucenter.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zwb.lib_common.adapter.MoyuAdapter
import com.zwb.lib_common.base.BaseListFragment
import com.zwb.lib_common.bean.MoyuItemBean
import com.zwb.lib_common.service.moyu.wrap.MoyuServiceWrap
import com.zwb.sob_ucenter.UcenterApi
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.databinding.UcenterFragmentListBinding

class UserCenterMoyuFragment :
    BaseListFragment<MoyuItemBean, UcenterFragmentListBinding, UcenterViewModel>(),
    BaseListFragment.RecyclerListener {
    override val mViewModel by viewModels<UcenterViewModel>()

    lateinit var mAdapter: MoyuAdapter

    lateinit var userId: String

    override fun UcenterFragmentListBinding.initView() {
        userId = requireArguments().getString("userId", "")
        mAdapter = MoyuAdapter(mutableListOf())
        this.rvList.layoutManager = LinearLayoutManager(mContext)
        this.rvList.adapter = mAdapter
        init(mAdapter, this.rvList, this.refreshLayout, this@UserCenterMoyuFragment)

        mAdapter.setOnItemClickListener { adapter, view, position ->
            MoyuServiceWrap.instance.launchDetail(adapter.getItem(position) as MoyuItemBean)
        }
    }

    override fun loadKey(): String {
        return UcenterApi.USER_MOYU_LIST_URL
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        mViewModel.getUserMoyuList(userId, page, loadKey()).observe(viewLifecycleOwner, {
            if(it==null){
                loadCompleted(action, null)
            }else{
                loadCompleted(action, list = it, pageSize = 30)
            }
        })
    }

}