package com.zwb.sob_shop.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.zwb.lib_common.base.BaseListFragment
import com.zwb.sob_shop.R
import com.zwb.sob_shop.ShopApi
import com.zwb.sob_shop.ShopViewModel
import com.zwb.sob_shop.adapter.ShopGoodsAdapter
import com.zwb.sob_shop.bean.ShopItemGoodsBean
import com.zwb.sob_shop.databinding.ShopFragmentListBinding

class ShopFragmentList: BaseListFragment<ShopItemGoodsBean, ShopFragmentListBinding, ShopViewModel>(),
    BaseListFragment.RecyclerListener {
    override val mViewModel by viewModels<ShopViewModel>()

    lateinit var mainTab: String
    var categoryId: Long = 0L

    private lateinit var mAdapter: ShopGoodsAdapter

    override fun ShopFragmentListBinding.initView() {
        mainTab = requireArguments().getString("main_tab", ShopMainFragment.MAIN_TAB_1)
        categoryId = requireArguments().getLong("categoryId", 0)

        mAdapter = ShopGoodsAdapter(mutableListOf())
        this.rvList.setHasFixedSize(true)
        this.rvList.layoutManager = LinearLayoutManager(mContext)
        this.rvList.adapter = mAdapter
        init(mAdapter, this.rvList, this.refreshLayout, this@ShopFragmentList)

        mAdapter.setOnItemClickListener { adapter, view, position ->
            toast("详情")
        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            if(view.id == R.id.btn_coupon){
                toast("领券")
            }
        }
    }

    override fun loadKey(): String {
        return if(mainTab == ShopMainFragment.MAIN_TAB_1){
            ShopApi.DISCOVERY_LIST_URL
        }else{
            ShopApi.RECOMMEND_LIST_URL
        }
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        mViewModel.shopGoodsList(mainTab,categoryId,page,loadKey()).observe(viewLifecycleOwner,{
            loadCompleted(action,it,52)
        })
    }
}