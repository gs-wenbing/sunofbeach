package com.zwb.sob_home.activity

import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.EventBusUtils
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.event.StringEvent
import com.zwb.sob_home.HomeViewModel
import com.zwb.sob_home.adapter.ChannelAdapter
import com.zwb.sob_home.bean.CategoryBean
import com.zwb.sob_home.databinding.HomeActivityChannelBinding


@Route(path = RoutePath.Home.PAGE_CHANNEL)
class ChannelManageActivity : BaseActivity<HomeActivityChannelBinding, HomeViewModel>() {
    override val mViewModel by viewModels<HomeViewModel>()

    private lateinit var mMyAdapter: ChannelAdapter
    private lateinit var mMoreAdapter: ChannelAdapter

    override fun HomeActivityChannelBinding.initView() {
        this.includeBar.tvTitle.text = "频道设置"
        this.includeBar.layoutRight.gone()
        mMyAdapter = ChannelAdapter(1, mutableListOf())
        mMoreAdapter = ChannelAdapter(2, mutableListOf())
        this.rvMyChannel.layoutManager = GridLayoutManager(this@ChannelManageActivity, 3)
        this.rvMyChannel.adapter = mMyAdapter

        this.rvMoreChannel.layoutManager = GridLayoutManager(this@ChannelManageActivity, 3)
        this.rvMoreChannel.adapter = mMoreAdapter

        initListener()
    }

    private fun initListener() {
        mBinding.includeBar.ivBack.setOnClickListener {
            EventBusUtils.postEvent(StringEvent(StringEvent.Event.UPDATE_HOME_TAB))
            finish()
        }
        mMyAdapter.setOnItemClickListener { adapter, view, position ->
            val item = mMyAdapter.getItem(position) as CategoryBean
            item.isMy = false
            mMoreAdapter.addData(item)
            mMyAdapter.remove(position)
            mViewModel.updateChannelFromDb(item)
        }
        mMoreAdapter.setOnItemClickListener { adapter, view, position ->
            val item = mMoreAdapter.getItem(position) as CategoryBean
            item.isMy = true
            mMoreAdapter.remove(position)
            mMyAdapter.addData(item)
            mViewModel.updateChannelFromDb(item)
        }
    }

    override fun initObserve() {
    }

    override fun setStatusBar() {
        super.setStatusBar()
        StatusBarUtil.setPaddingSmart(this, mBinding.includeBar.toolbar)
    }

    override fun initRequestData() {
        mViewModel.getCategoryFromDb().observe(this) {
            it?.let {
                mMyAdapter.addData(CategoryBean("1", "推荐", 1))
                mMyAdapter.addData(it.filter { item-> item.isMy })
                if(it.all { item-> item.isMy }){
                    mBinding.moreChannel.setCenterString("暂无更多频道")
                }else{
                    mMoreAdapter.addData(it.filter { item-> !item.isMy })
                }
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        EventBusUtils.postEvent(StringEvent(StringEvent.Event.UPDATE_HOME_TAB))
    }
}