package com.zwb.sob_ucenter.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zwb.lib_common.CommonApi
import com.zwb.lib_common.base.BaseListFragment
import com.zwb.lib_common.bean.CollectionBean
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.sob_ucenter.UcenterApi
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.adapter.CollectionFolderAdapter
import com.zwb.sob_ucenter.databinding.UcenterFragmentCollectionListBinding

class UserCollectionListFragment :
    BaseListFragment<CollectionBean, UcenterFragmentCollectionListBinding, UcenterViewModel>(),
    BaseListFragment.RecyclerListener {

    override val mViewModel by viewModels<UcenterViewModel>()

    private lateinit var mAdapter: CollectionFolderAdapter

    private var userId: String? = null

    override fun UcenterFragmentCollectionListBinding.initView() {
        userId = requireArguments().getString("userId")

        mAdapter = CollectionFolderAdapter(mutableListOf())
        this.rvList.setHasFixedSize(true)
        this.rvList.layoutManager = LinearLayoutManager(mContext)
        this.rvList.adapter = mAdapter
        init(mAdapter, this.rvList, this.refreshLayout, this@UserCollectionListFragment)

        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as CollectionBean
            UcenterServiceWrap.instance.launchFavoriteList(item)
        }
    }

    override fun loadKey(): String {
        return CommonApi.COLLECTION_LIST_URL
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        mViewModel.collectionList(page, loadKey()).observe(viewLifecycleOwner, {
            it?.let {
                loadCompleted(action, list = it, pageSize = 20)
            }
        })
    }
}