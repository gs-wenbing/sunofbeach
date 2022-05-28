package com.zwb.sob_shop.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.zwb.lib_base.ktx.reduceDragSensitivity
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.sob_shop.ShopViewModel
import com.zwb.sob_shop.databinding.ShopFragmentMainBinding

class ShopMainFragment:BaseFragment<ShopFragmentMainBinding,ShopViewModel>() {
    override val mViewModel by viewModels<ShopViewModel>()

    private var titles = mutableListOf<String>()
    private val fragments: MutableList<Class<*>> = mutableListOf()

    private lateinit var mAdapter: FragmentStateAdapter

    override fun ShopFragmentMainBinding.initView() {
        StatusBarUtil.immersive(requireActivity())
        StatusBarUtil.darkMode(requireActivity(), true)
        StatusBarUtil.setPaddingSmart(requireContext(), mBinding.relativeLayout)
        fragments.add(ShopFragmentSecond::class.java)
        fragments.add(ShopFragmentSecond::class.java)
        titles.add(MAIN_TAB_1)
        titles.add(MAIN_TAB_2)

        mAdapter = object : FragmentStateAdapter(this@ShopMainFragment) {
            override fun getItemCount(): Int = fragments.size
            override fun createFragment(position: Int): Fragment {
                return try {
                    val fragment = fragments[position].newInstance() as Fragment
                    val bundle = Bundle()
                    bundle.putString("main_tab", titles[position])
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
            tab.text = titles[position]
        }.attach()

    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }

    companion object{
        const val MAIN_TAB_1 = "发现"
        const val MAIN_TAB_2 = "精选"
    }
}