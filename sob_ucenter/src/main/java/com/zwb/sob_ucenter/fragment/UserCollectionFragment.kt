package com.zwb.sob_ucenter.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.databinding.UcenterFragmentCollectionBinding

class UserCollectionFragment:BaseFragment<UcenterFragmentCollectionBinding,UcenterViewModel>() {

    override val mViewModel by viewModels<UcenterViewModel>()

    private var titles = mutableListOf<String>()

    private val fragments: MutableList<Class<*>> = mutableListOf()

    private lateinit var mAdapter: FragmentStateAdapter

    private var userId: String? = null

    override fun UcenterFragmentCollectionBinding.initView() {

        userId = requireArguments().getString("userId")

        titles.add("创建的")
        titles.add("关注的")
        fragments.add(UserCollectionListFragment::class.java)
        fragments.add(UserCollectionListFragment::class.java)

        mAdapter = object : FragmentStateAdapter(this@UserCollectionFragment) {
            override fun getItemCount(): Int = fragments.size
            override fun createFragment(position: Int): Fragment {
                return try {
                    val fragment = fragments[position].newInstance() as Fragment
                    val bundle = Bundle()
                    bundle.putString("userId", userId)
//                    when(position){
//                        0->bundle.putString(WENDA_TYPE, WENDA_LASTEST)
//                        1->bundle.putString(WENDA_TYPE, WENDA_HOT)
//                    }
                    fragment.arguments = bundle
                    return fragment
                } catch (e: Exception) {
                    e.printStackTrace()
                    Fragment()
                }
            }
        }
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