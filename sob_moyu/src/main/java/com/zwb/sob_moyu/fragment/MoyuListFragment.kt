package com.zwb.sob_moyu.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_common.base.BaseListFragment
import com.zwb.lib_common.adapter.MoyuAdapter
import com.zwb.sob_moyu.MoyuApi
import com.zwb.sob_moyu.MoyuViewModel
import com.zwb.lib_common.bean.MoyuItemBean
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.moyu.wrap.MoyuServiceWrap
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.sob_moyu.R
import com.zwb.sob_moyu.databinding.MoyuFragmentListBinding

class MoyuListFragment : BaseListFragment<MoyuItemBean,MoyuFragmentListBinding, MoyuViewModel>(),
    BaseListFragment.RecyclerListener {

    override val mViewModel by viewModels<MoyuViewModel>()

    private lateinit var topicId: String

    private lateinit var mAdapter: MoyuAdapter

    override fun MoyuFragmentListBinding.initView() {
        topicId = requireArguments().getString("topicId", "1")
        mAdapter = MoyuAdapter(mutableListOf())
        this.rvList.setHasFixedSize(true)
        this.rvList.layoutManager = LinearLayoutManager(mContext)
        this.rvList.adapter = mAdapter
        init(mAdapter, this.rvList, this.refreshLayout, this@MoyuListFragment)

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as MoyuItemBean
            if(view.id == com.zwb.lib_common.R.id.iv_avatar || view.id ==R.id.tv_nickname){
                UcenterServiceWrap.instance.launchDetail(item.userId)
            }
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as MoyuItemBean
            MoyuServiceWrap.instance.launchDetail(item)
        }
    }

    override fun loadKey(): String {
        return when(topicId){
            "1" -> MoyuApi.RECOMMEND_LIST_URL
            "2" -> MoyuApi.FOLLOW_LIST_URL
            else -> MoyuApi.LIST_URL
        }
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        mViewModel.getList(topicId,page,loadKey()).observe(viewLifecycleOwner,{
            it?.let {
                loadCompleted(action, list = it.list, pageSize = it.pageSize)
            }
        })
    }




}