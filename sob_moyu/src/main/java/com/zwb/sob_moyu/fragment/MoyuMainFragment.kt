package com.zwb.sob_moyu.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.zwb.lib_base.ktx.reduceDragSensitivity
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.sob_moyu.MoyuApi
import com.zwb.sob_moyu.MoyuViewModel
import com.zwb.sob_moyu.bean.TopicIndexBean
import com.zwb.sob_moyu.databinding.MoyuFragmentMainBinding

class MoyuMainFragment : BaseFragment<MoyuFragmentMainBinding, MoyuViewModel>() {

    override val mViewModel by viewModels<MoyuViewModel>()

    private var titles = mutableListOf<TopicIndexBean>()
    private val fragments: MutableList<Class<*>> = mutableListOf()

    private lateinit var mAdapter:FragmentStateAdapter

    override fun MoyuFragmentMainBinding.initView() {
        StatusBarUtil.immersive(requireActivity())
        StatusBarUtil.darkMode(requireActivity(), true)
        StatusBarUtil.setPaddingSmart(requireContext(), mBinding.relativeLayout)

        titles.add(TopicIndexBean("1","推荐"))
        titles.add(TopicIndexBean("2","关注"))

        fragments.add(MoyuListFragment::class.java)
        fragments.add(MoyuListFragment::class.java)
        mAdapter = object : FragmentStateAdapter(this@MoyuMainFragment) {
            override fun getItemCount(): Int = fragments.size
            override fun createFragment(position: Int): Fragment {
                return try {
                    val fragment = fragments[position].newInstance() as Fragment
                    val bundle = Bundle()
                    bundle.putString("topicId", titles[position].id)
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
            tab.text = titles[position].topicName
        }.attach()
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        mViewModel.topicIndex(MoyuApi.TOPIC_INDEX_URL).observe(this, {
            it?.let {
                it.forEach { topic ->
                    fragments.add(MoyuListFragment::class.java)
                    titles.add(topic)
                }
                mAdapter.notifyDataSetChanged()
            }
        })
    }

}