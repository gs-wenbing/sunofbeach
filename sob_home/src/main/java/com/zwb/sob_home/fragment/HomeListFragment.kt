package com.zwb.sob_home.fragment

import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import com.zwb.lib_common.base.BaseListFragment
import com.zwb.lib_common.service.home.wrap.HomeServiceWrap
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.sob_home.HomeApi
import com.zwb.sob_home.HomeViewModel
import com.zwb.sob_home.R
import com.zwb.sob_home.adapter.HomeAdapter
import com.zwb.sob_home.adapter.HomeBannerAdapter
import com.zwb.sob_home.bean.BannerBean
import com.zwb.sob_home.bean.BannerList
import com.zwb.sob_home.bean.HomeItemBean
import com.zwb.sob_home.databinding.HomeBannerLayoutBinding
import com.zwb.sob_home.databinding.HomeFragmentListBinding

class HomeListFragment :
    BaseListFragment<MultiItemEntity, HomeFragmentListBinding, HomeViewModel>(),
    BaseListFragment.RecyclerListener {

    override val mViewModel by viewModels<HomeViewModel>()

    private lateinit var categoryId: String
    private lateinit var mAdapter: HomeAdapter
    private lateinit var bannerBinding: HomeBannerLayoutBinding

    override fun HomeFragmentListBinding.initView() {
        categoryId = requireArguments().getString("categoryId", "1")
        mAdapter = HomeAdapter(mutableListOf())

        this.rvList.layoutManager = LinearLayoutManager(mContext)
        this.rvList.adapter = mAdapter
        init(mAdapter, this.rvList, this.refreshLayout, this@HomeListFragment)

        mAdapter.setOnItemClickListener { adapter, _, position ->
            if(adapter.getItem(position) is HomeItemBean){
                val item = adapter.getItem(position) as HomeItemBean
                HomeServiceWrap.instance.launchDetail(item.id , item.title)
            }
        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as HomeItemBean
            when (view.id) {
                R.id.tv_content -> {
                    HomeServiceWrap.instance.launchDetail(item.id , item.title)
                }
                R.id.iv_avatar, R.id.tv_nickName -> {
                    UcenterServiceWrap.instance.launchDetail(item.userId)
                }
            }
        }
        loadBanner()
    }


    override fun loadKey(): String {
        return HomeApi.RECOMMEND_URL
    }

    override fun loadListData(action: Int, pageSize: Int, page: Int) {
        mViewModel.getList(categoryId, page, loadKey()).observe(viewLifecycleOwner, {
            it?.let {
                loadCompleted(action, list = it.list, pageSize = it.pageSize)
            }
        })
    }

    private fun loadBanner(){
        if (categoryId == "1") {
            bannerBinding = HomeBannerLayoutBinding.inflate(layoutInflater)
            mAdapter.addHeaderView(bannerBinding.root)

            mViewModel.getBanner(HomeApi.BANNER_URL).observe(viewLifecycleOwner, {
                if(activity==null || requireActivity().isFinishing){
                    return@observe
                }
                it?.let {
                    val bannerAdapter = HomeBannerAdapter(mutableListOf())
                    bannerBinding.banner.adapter = bannerAdapter
                    bannerBinding.banner.addBannerLifecycleObserver(this)
                    // 画廊效果
                    bannerBinding.banner.setBannerGalleryEffect(16,6,0.8f)
                    bannerAdapter.setDatas(it.bannerList)
                    bannerBinding.banner.setCurrentItem(1,true)

                    bannerBinding.banner.indicator = CircleIndicator(requireContext())
                    bannerBinding.banner.setIndicatorSelectedColor(ContextCompat.getColor(mContext,R.color.colorAccent))
                    bannerAdapter.setOnBannerListener { data, position ->

                    }
                }
            })
        }
    }
}