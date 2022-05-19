package com.zwb.sob_ucenter.fragment

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.zwb.lib_base.utils.EventBusRegister
import com.zwb.lib_base.utils.EventBusUtils
import com.zwb.lib_common.base.BaseListFragment
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.event.StringEvent
import com.zwb.lib_common.service.moyu.wrap.MoyuServiceWrap
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.sob_ucenter.R
import com.zwb.sob_ucenter.UcenterApi
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.adapter.MsgListAdapter
import com.zwb.sob_ucenter.bean.MsgAtBean
import com.zwb.sob_ucenter.bean.MsgBean
import com.zwb.sob_ucenter.bean.MsgMomentBean
import com.zwb.sob_ucenter.bean.MsgThumbBean
import com.zwb.sob_ucenter.databinding.UcenterFragmentListBinding

class UserMessageListFragment :
    BaseListFragment<MsgBean, UcenterFragmentListBinding, UcenterViewModel>(),
    BaseListFragment.RecyclerListener {

    override val mViewModel by viewModels<UcenterViewModel>()

    private lateinit var mAdapter: MsgListAdapter

    var pageType: Int = 0

    override fun UcenterFragmentListBinding.initView() {

        pageType = requireArguments().getInt("pageType")

        mAdapter = MsgListAdapter(mutableListOf())
        this.rvList.setHasFixedSize(true)
        this.rvList.layoutManager = LinearLayoutManager(mContext)
        this.rvList.adapter = mAdapter
        init(mAdapter, this.rvList, this.refreshLayout, this@UserMessageListFragment)

        initListener()
    }

    private fun initListener() {
        mAdapter.setOnItemClickListener { adapter, view, position ->
            when (val item = adapter.getItem(position)) {
                is MsgAtBean -> {
                    if (item.hasRead == "0") {
                        updateState(item, position)
                    }
                    MoyuServiceWrap.instance.launchDetail(item.exId)
                }
                is MsgMomentBean -> {
                    if (item.hasRead == "0") {
                        updateState(item, position)
                    }
                    MoyuServiceWrap.instance.launchDetail(item.momentId)
                }
                is MsgThumbBean -> {
                    val arr = item.url.split("/")
                    if (arr.size == 3) {
                        MoyuServiceWrap.instance.launchDetail(arr[2])
                    }
                }
            }
        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            if (view.id == R.id.iv_avatar) {
                val uid = when (val item = adapter.getItem(position)) {
                    is MsgAtBean -> item.uid
                    is MsgMomentBean -> item.uid
                    is MsgThumbBean -> item.uid
                    else -> ""
                }
                UcenterServiceWrap.instance.launchDetail(uid)
            }
        }
    }

    override fun loadKey(): String {
        when (pageType) {
            Constants.Ucenter.PAGE_MSG_DYNAMIC -> return UcenterApi.MESSAGE_MOMENT_URL
            Constants.Ucenter.PAGE_MSG_STAR -> return UcenterApi.MESSAGE_THUMB_URL
            Constants.Ucenter.PAGE_MSG_AT -> return UcenterApi.MESSAGE_AT_URL
        }
        return UcenterApi.MESSAGE_MOMENT_URL
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        when (pageType) {
            Constants.Ucenter.PAGE_MSG_DYNAMIC -> return messageMomentList(action, page)
            Constants.Ucenter.PAGE_MSG_STAR -> return messageThumbList(action, page)
            Constants.Ucenter.PAGE_MSG_AT -> return messageAtList(action, page)
        }
    }

    private fun messageAtList(action: Int, page: Int) {
        mViewModel.messageAtList(page, loadKey()).observe(viewLifecycleOwner, {
            it?.let {
                loadCompleted(action, list = it.content)
            }
        })
    }

    private fun messageMomentList(action: Int, page: Int) {
        mViewModel.messageMomentList(page, loadKey()).observe(viewLifecycleOwner, {
            it?.let {
                loadCompleted(action, list = it.content)
            }
        })
    }

    private fun messageThumbList(action: Int, page: Int) {
        mViewModel.messageThumbList(page, loadKey()).observe(viewLifecycleOwner, {
            it?.let {
                loadCompleted(action, list = it.content)
                EventBusUtils.postEvent(StringEvent(StringEvent.Event.MSG_READ))
            }
        })
    }

    private fun updateState(msgBean: MsgBean, position: Int) {
        val id = when (msgBean) {
            is MsgAtBean -> msgBean._id
            is MsgMomentBean -> msgBean._id
            else -> ""
        }
        mViewModel.updateMsgState(pageType, id).observe(viewLifecycleOwner, {
            if (it.success) {
                EventBusUtils.postEvent(StringEvent(StringEvent.Event.MSG_READ))
                when (msgBean) {
                    is MsgAtBean -> msgBean.hasRead = "1"
                    is MsgMomentBean -> msgBean.hasRead = "1"
                }
                mAdapter.notifyItemChanged(position)
            }
        })
    }
}