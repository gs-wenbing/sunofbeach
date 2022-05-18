package com.zwb.sob_ucenter.fragment

import android.annotation.SuppressLint
import android.text.TextUtils
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zwb.lib_common.base.BaseListFragment
import com.zwb.sob_ucenter.UcenterApi
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.adapter.SobAdapter
import com.zwb.sob_ucenter.bean.SobBean
import com.zwb.sob_ucenter.databinding.UcenterFragmentListBinding
import com.zwb.sob_ucenter.databinding.UcenterSobHeaderBinding

class UserSobFragment :
    BaseListFragment<SobBean, UcenterFragmentListBinding, UcenterViewModel>(),
    BaseListFragment.RecyclerListener {

    override val mViewModel by viewModels<UcenterViewModel>()

    private lateinit var mAdapter: SobAdapter

    private var userId: String? = null

    private lateinit var headerBinding: UcenterSobHeaderBinding

    override fun UcenterFragmentListBinding.initView() {
        userId = requireArguments().getString("userId")
        mAdapter = SobAdapter(mutableListOf())
        headerBinding = UcenterSobHeaderBinding.inflate(layoutInflater)
        mAdapter.addHeaderView(headerBinding.root)
        this.rvList.setHasFixedSize(true)
        this.rvList.layoutManager = LinearLayoutManager(mContext)
        this.rvList.adapter = mAdapter
        init(mAdapter, this.rvList, this.refreshLayout, this@UserSobFragment)
        getTotalSob()
    }

    override fun loadKey(): String {
        return UcenterApi.SOB_LIST_URL
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        if(!TextUtils.isEmpty(userId)){
            mViewModel.getSobList(userId!!, page, loadKey()).observe(viewLifecycleOwner, {
                it?.let {
                    loadCompleted(action, list = it.list, pageSize = it.pageSize)
                }
            })
        } else {
            loadCompleted(action, list = null)
        }
    }
    @SuppressLint("SetTextI18n")
    private fun getTotalSob(){
        mViewModel.getTotalSobCoin().observe(viewLifecycleOwner,{
            if(it.success){
                headerBinding.tvHeaderSob.text = "${it.data.toString()} 币"
            }
        })
    }
}