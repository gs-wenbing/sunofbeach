package com.zwb.sob_wenda.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_common.base.BaseListFragment
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.lib_common.service.wenda.wrap.WendaServiceWrap
import com.zwb.sob_wenda.R
import com.zwb.sob_wenda.WendaApi
import com.zwb.sob_wenda.WendaViewModel
import com.zwb.sob_wenda.adapter.WendaAdapter
import com.zwb.sob_wenda.bean.WendaBean
import com.zwb.sob_wenda.databinding.WendaFragmentListBinding

class WendaListFragment : BaseListFragment<WendaBean, WendaFragmentListBinding, WendaViewModel>(),
    BaseListFragment.RecyclerListener {

    override val mViewModel by viewModels<WendaViewModel>()

    private lateinit var mAdapter: WendaAdapter
    private lateinit var wendaType:String

    override fun WendaFragmentListBinding.initView() {
        wendaType = requireArguments().getString(WENDA_TYPE, WENDA_LASTEST)
        mAdapter = WendaAdapter(mutableListOf())
        this.rvList.setHasFixedSize(true)
        this.rvList.layoutManager = LinearLayoutManager(mContext)
        this.rvList.adapter = mAdapter
        init(mAdapter, this.rvList, this.refreshLayout, this@WendaListFragment)

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as WendaBean
            if(view.id == R.id.iv_avatar || view.id == R.id.tv_nickname){
                UcenterServiceWrap.instance.launchDetail(item.userId)
            }
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as WendaBean
            WendaServiceWrap.instance.launchDetail(item.id)
        }
    }

    override fun loadKey(): String {
        return "${WendaApi.WENDA_LIST_URL}?state=$wendaType"
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        mViewModel.getWendaList(page,wendaType,loadKey()).observe(viewLifecycleOwner,{
            it?.let {
                loadCompleted(action, list = it.list, pageSize = it.pageSize)
            }
        })
    }

    companion object{
        const val WENDA_TYPE = "wenda_type"
        const val WENDA_LASTEST = "lastest"
        const val WENDA_HOT = "hot"
    }
}