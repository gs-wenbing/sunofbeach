package com.zwb.sob_shop.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.zwb.lib_base.ktx.reduceDragSensitivity
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.sob_shop.ShopApi
import com.zwb.sob_shop.ShopViewModel
import com.zwb.sob_shop.bean.DiscoverCategoryBean
import com.zwb.sob_shop.bean.RecommendCategoriesBean
import com.zwb.sob_shop.bean.ShopCategoryBean
import com.zwb.sob_shop.databinding.ShopFragmentSecondBinding
import com.zwb.sob_shop.fragment.ShopMainFragment.Companion.MAIN_TAB_1

class ShopFragmentSecond : BaseFragment<ShopFragmentSecondBinding, ShopViewModel>() {
    override val mViewModel by viewModels<ShopViewModel>()
    private var titles = mutableListOf<ShopCategoryBean>()
    private val fragments: MutableList<Class<*>> = mutableListOf()

    lateinit var mainTab: String

    private lateinit var mAdapter: FragmentStateAdapter
    override fun ShopFragmentSecondBinding.initView() {
        mainTab = requireArguments().getString("main_tab", MAIN_TAB_1)

    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        mViewModel.categoryList(mainTab, ShopApi.DISCOVERY_CATEGORIES_URL)
            .observe(viewLifecycleOwner, {
                it?.let { list ->
                    titles = list.toMutableList()
                    titles.forEach { _ ->
                        fragments.add(ShopFragmentList::class.java)
                    }
                    initViewData()
                }
            })
    }

    private fun initViewData() {
        mAdapter = object : FragmentStateAdapter(this@ShopFragmentSecond) {
            override fun getItemCount(): Int = fragments.size
            override fun createFragment(position: Int): Fragment {
                return try {
                    val fragment = fragments[position].newInstance() as Fragment
                    val bundle = Bundle()
                    bundle.putString("main_tab", mainTab)
                    if (titles[position] is DiscoverCategoryBean) {
                        bundle.putLong(
                            "categoryId",
                            (titles[position] as DiscoverCategoryBean).id
                        )
                    } else {
                        bundle.putLong(
                            "categoryId",
                            (titles[position] as RecommendCategoriesBean).favorites_id
                        )
                    }
                    fragment.arguments = bundle
                    return fragment
                } catch (e: Exception) {
                    e.printStackTrace()
                    Fragment()
                }
            }
        }
        mBinding.vpContent2.adapter = mAdapter
        mBinding.vpContent2.reduceDragSensitivity()
        TabLayoutMediator(mBinding.tabLayout2, mBinding.vpContent2) { tab, position ->
            if (titles[position] is DiscoverCategoryBean) {
                tab.text = (titles[position] as DiscoverCategoryBean).title
            } else {
                tab.text = (titles[position] as RecommendCategoriesBean).favorites_title
            }

        }.attach()
    }
}