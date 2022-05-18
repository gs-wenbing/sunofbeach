package com.zwb.sob_wenda.fragment

import android.annotation.SuppressLint
import android.view.ViewGroup
import android.view.ViewGroup.MarginLayoutParams
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.ktx.height
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.ktx.width
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.sob_wenda.R
import com.zwb.sob_wenda.WendaApi
import com.zwb.sob_wenda.WendaViewModel
import com.zwb.sob_wenda.adapter.WendaRankingAdapter
import com.zwb.sob_wenda.bean.WendaRankingBean
import com.zwb.sob_wenda.databinding.WendaFragmentListBinding
import com.zwb.sob_wenda.databinding.WendaRankingHeaderBinding
import com.zwb.sob_wenda.databinding.WendaRankingHeaderItemBinding


class WendaRankingFragment : BaseFragment<WendaFragmentListBinding, WendaViewModel>() {

    override val mViewModel by viewModels<WendaViewModel>()

    lateinit var mAdapter: WendaRankingAdapter

    lateinit var headerBinding: WendaRankingHeaderBinding

    private val w60 = UIUtils.dp2px(60f)
    private val w40 = UIUtils.dp2px(40f)
    private val w5 = UIUtils.dp2px(5f)

    override fun WendaFragmentListBinding.initView() {
        mAdapter = WendaRankingAdapter(mutableListOf())

        headerBinding = WendaRankingHeaderBinding.inflate(layoutInflater)

        mAdapter.addHeaderView(headerBinding.root)

        this.refreshLayout.setEnableRefresh(false)
//        this.refreshLayout.setRefreshHeader(ClassicsHeader(requireContext()))
//        this.refreshLayout.setOnRefreshListener {
//            initRequestData()
//        }
        this.rvList.setHasFixedSize(true)
        this.rvList.layoutManager = LinearLayoutManager(mContext)
        this.rvList.adapter = mAdapter

        setDefaultLoad(this.refreshLayout, WendaApi.WENDA_RANKING_LIST_URL)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as WendaRankingBean
            UcenterServiceWrap.instance.launchDetail(item.userId)
        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as WendaRankingBean
            if (view.id == R.id.btn_follow) {
                UcenterServiceWrap.instance.launchDetail(item.userId)
            }
        }
    }


    @SuppressLint("SetTextI18n")
    private fun initHeaderView(item: WendaRankingBean, num: Int) {
        val itemBinding = WendaRankingHeaderItemBinding.inflate(layoutInflater)
        val params = MarginLayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        params.setMargins(w5, 0, w5, 0)
        itemBinding.root.layoutParams = params

        if (num == 1) {
            itemBinding.ivAvatar.width(w60)
            itemBinding.ivAvatar.height(w60)
            itemBinding.root.setBackgroundResource(R.drawable.wenda_shape_gold_background)
            itemBinding.ivRanking.setImageResource(R.mipmap.ic_gold)
        } else {
            itemBinding.ivAvatar.width(w40)
            itemBinding.ivAvatar.height(w40)
            itemBinding.root.setBackgroundResource(if (num == 2) R.drawable.wenda_shape_silver_background else R.drawable.wenda_shape_copper_background)
            itemBinding.ivRanking.setImageResource(if (num == 2) R.mipmap.ic_silver else R.mipmap.ic_copper)
        }
        itemBinding.ivAvatar.loadAvatar(item.vip,item.avatar)
        itemBinding.root.width((UIUtils.getScreenWidth() - w40) / 3)

        itemBinding.tvNickname.text = item.nickname
        itemBinding.tvCount.text = "${item.count} 个回答"
        headerBinding.root.addView(itemBinding.root)
        itemBinding.root.setOnClickListener {
            UcenterServiceWrap.instance.launchDetail(item.userId)
        }
        itemBinding.btnFollow.setOnClickListener {
            follow(item.userId, itemBinding.btnFollow)
        }
        getFollowState(item.userId, itemBinding.btnFollow)
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        mViewModel.getWendaRankingList(WendaApi.WENDA_RANKING_LIST_URL).observe(viewLifecycleOwner,
            {
                headerBinding.root.removeAllViews()
//                mBinding.refreshLayout.finishRefresh()
                it?.let {
                    if (it.size > 3) {
                        initHeaderView(it[1], 2)
                        initHeaderView(it[0], 1)
                        initHeaderView(it[2], 3)
                        mAdapter.setNewData(it.subList(3, it.size))
                    } else {
                        mAdapter.setNewData(it)
                    }
                }

            })
    }

    private fun getFollowState(userId: String, btn: TextView) {
        mViewModel.followState(userId).observe(this, {
            if (it.success && it.data != null) {
                CommonViewUtils.setFollowState(btn, it.data!!)
            }
        })
    }

    /**
     * 关注
     */
    private fun follow(userId: String, btn: TextView) {
        mViewModel.follow(userId).observe(this, {
            getFollowState(userId, btn)
        })
    }
}