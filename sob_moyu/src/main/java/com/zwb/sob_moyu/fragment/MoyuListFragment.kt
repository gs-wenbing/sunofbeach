package com.zwb.sob_moyu.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zwb.lib_base.utils.EventBusRegister
import com.zwb.lib_common.adapter.MoyuAdapter
import com.zwb.lib_common.base.BaseListFragment
import com.zwb.lib_common.bean.MoyuItemBean
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.event.StringEvent
import com.zwb.lib_common.event.UpdateItemEvent
import com.zwb.lib_common.service.home.wrap.HomeServiceWrap
import com.zwb.lib_common.service.moyu.wrap.MoyuServiceWrap
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.sob_moyu.MoyuApi
import com.zwb.sob_moyu.MoyuViewModel
import com.zwb.sob_moyu.R
import com.zwb.sob_moyu.databinding.MoyuFragmentListBinding
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@EventBusRegister
class MoyuListFragment : BaseListFragment<MoyuItemBean,MoyuFragmentListBinding, MoyuViewModel>(),
    BaseListFragment.RecyclerListener {

    override val mViewModel by viewModels<MoyuViewModel>()

    private lateinit var topicId: String

    private lateinit var mAdapter: MoyuAdapter

    override fun MoyuFragmentListBinding.initView() {
        topicId = requireArguments().getString("topicId", "1")
        mAdapter = MoyuAdapter(this@MoyuListFragment, mutableListOf())
        this.rvList.setHasFixedSize(true)
        this.rvList.layoutManager = LinearLayoutManager(mContext)
        this.rvList.adapter = mAdapter
        init(mAdapter, this.rvList, this.refreshLayout, this@MoyuListFragment)

        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as MoyuItemBean
            if(view.id == R.id.iv_avatar || view.id ==R.id.tv_nickname){
                UcenterServiceWrap.instance.launchDetail(item.userId)
            }else if(view.id == R.id.tv_link){
                item.linkUrl?.let {
                    CommonViewUtils.toWebView(it)
                }
            }
        }

        mAdapter.setOnItemClickListener { adapter, view, position ->
            when(val item = adapter.getItem(position)){
                is MoyuItemBean -> {
                    MoyuServiceWrap.instance.launchDetail(item.id)
                }
                is String->{

                }
            }

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

    /**
     * 更新某一条item
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventUpdateItem(event: UpdateItemEvent){
        when (event.event) {
            UpdateItemEvent.Event.UPDATE_MOYU -> {
                updateItem(event.id)
            }
        }
    }

    private fun updateItem(id: String){
        mViewModel.moyuDetail(id,"").observe(viewLifecycleOwner,{ moyu ->
            if(moyu!=null){
                val index = mAdapter.data.indexOf(moyu)
                val item = mAdapter.data[index]
                moyu.commentCount = item?.commentCount?:moyu.commentCount
                moyu.thumbUpCount = item?.thumbUpCount?:moyu.thumbUpCount
                moyu.hasThumbUp = item?.hasThumbUp?:moyu.hasThumbUp
                mAdapter.notifyItemChanged(index)
            }
        })
    }


}