package com.zwb.sob_home.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.zwb.lib_base.ktx.reduceDragSensitivity
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.sob_home.HomeApi
import com.zwb.sob_home.HomeViewModel
import com.zwb.sob_home.bean.CategoryBean
import com.zwb.sob_home.databinding.HomeAdapterBinding
import com.zwb.sob_home.databinding.HomeFragmentMainBinding

class HomeMainFragment:BaseFragment<HomeFragmentMainBinding,HomeViewModel>() {
    override val mViewModel by viewModels<HomeViewModel>()

    private var titles = mutableListOf<CategoryBean>()
    private val fragments: MutableList<Class<*>> = mutableListOf()

    private lateinit var mAdapter: FragmentStateAdapter

    override fun HomeFragmentMainBinding.initView() {
        StatusBarUtil.immersive(requireActivity())
        StatusBarUtil.darkMode(requireActivity(), true)
        StatusBarUtil.setPaddingSmart(requireContext(), mBinding.relativeLayout)

        titles.add(CategoryBean("1","推荐",0))

        fragments.add(HomeListFragment::class.java)
        mAdapter = object : FragmentStateAdapter(this@HomeMainFragment) {
            override fun getItemCount(): Int = fragments.size
            override fun createFragment(position: Int): Fragment {
                return try {
                    val fragment = fragments[position].newInstance() as Fragment
                    val bundle = Bundle()
                    bundle.putString("categoryId", titles[position].id)
                    fragment.arguments = bundle
                    return fragment
                } catch (e: Exception) {
                    e.printStackTrace()
                    Fragment()
                }
            }
        }
        mBinding.vpContent.adapter = mAdapter
        mBinding.vpContent.reduceDragSensitivity()
        TabLayoutMediator(mBinding.tabLayout, mBinding.vpContent) { tab, position ->
            tab.text = titles[position].categoryName
        }.attach()
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        mViewModel.categoryList(HomeApi.CATEGORY_LIST_URL).observe(this, {
            it?.let {
                it.forEach { category ->
                    fragments.add(HomeListFragment::class.java)
                    titles.add(category)
                }
                mAdapter.notifyDataSetChanged()
            }
        })
    }
}