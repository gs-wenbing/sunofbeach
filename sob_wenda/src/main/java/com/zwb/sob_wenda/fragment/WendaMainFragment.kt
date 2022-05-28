package com.zwb.sob_wenda.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.zwb.lib_base.ktx.reduceDragSensitivity
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.sob_wenda.WendaViewModel
import com.zwb.sob_wenda.databinding.WendaFragmentMainBinding
import com.zwb.sob_wenda.fragment.WendaListFragment.Companion.WENDA_HOT
import com.zwb.sob_wenda.fragment.WendaListFragment.Companion.WENDA_LASTEST
import com.zwb.sob_wenda.fragment.WendaListFragment.Companion.WENDA_TYPE

class WendaMainFragment:BaseFragment<WendaFragmentMainBinding, WendaViewModel>() {
    override val mViewModel by viewModels<WendaViewModel>()

    private var titles = mutableListOf<String>()
    private val fragments: MutableList<Class<*>> = mutableListOf()

    private lateinit var mAdapter: FragmentStateAdapter

    override fun WendaFragmentMainBinding.initView() {
        StatusBarUtil.immersive(requireActivity())
        StatusBarUtil.darkMode(requireActivity(), true)
        StatusBarUtil.setPaddingSmart(requireContext(), mBinding.tabLayout)

        titles.add("最新提问")
        titles.add("热门推荐")
        titles.add("周•排行榜")

        fragments.add(WendaListFragment::class.java)
        fragments.add(WendaListFragment::class.java)
        fragments.add(WendaRankingFragment::class.java)
        mAdapter = object : FragmentStateAdapter(this@WendaMainFragment) {
            override fun getItemCount(): Int = fragments.size
            override fun createFragment(position: Int): Fragment {
                return try {
                    val fragment = fragments[position].newInstance() as Fragment
                    val bundle = Bundle()
                    when(position){
                        0->bundle.putString(WENDA_TYPE, WENDA_LASTEST)
                        1->bundle.putString(WENDA_TYPE, WENDA_HOT)
                    }
                    fragment.arguments = bundle
                    return fragment
                } catch (e: Exception) {
                    e.printStackTrace()
                    Fragment()
                }
            }
        }
        mBinding.vpContent.reduceDragSensitivity()
        mBinding.vpContent.adapter = mAdapter
        TabLayoutMediator(mBinding.tabLayout, mBinding.vpContent) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }

    override fun initObserve() {

    }

    override fun initRequestData() {

    }
}