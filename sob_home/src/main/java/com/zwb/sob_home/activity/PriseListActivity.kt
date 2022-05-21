package com.zwb.sob_home.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_common.service.home.wrap.HomeServiceWrap
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.sob_home.HomeApi
import com.zwb.sob_home.HomeViewModel
import com.zwb.sob_home.adapter.PriseAdapter
import com.zwb.sob_home.bean.PriseArticleBean
import com.zwb.sob_home.databinding.HomeActivityPriseBinding

class PriseListActivity:BaseActivity<HomeActivityPriseBinding,HomeViewModel>() {
    override val mViewModel by viewModels<HomeViewModel>()

    lateinit var mAdapter: PriseAdapter

    override fun HomeActivityPriseBinding.initView() {
        mBinding.includeBar.tvTitle.text = "打赏列表"
        mBinding.includeBar.ll.gone()
        mBinding.includeBar.ivBack.setOnClickListener { finish() }

        mBinding.rvPrise.layoutManager = LinearLayoutManager(this@PriseListActivity)
        mAdapter = PriseAdapter(mutableListOf())
        mBinding.rvPrise.adapter = mAdapter

        setDefaultLoad(this.rvPrise, HomeApi.PRISE_ARTICLE_URL)
        mAdapter.setOnItemClickListener { adapter, view, position ->
            val item = adapter.getItem(position) as PriseArticleBean
            UcenterServiceWrap.instance.launchDetail(item.userId)
        }
    }
    override fun setStatusBar() {
        super.setStatusBar()
        StatusBarUtil.setPaddingSmart(this, mBinding.includeBar.toolbar)
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        val articleId = intent.getStringExtra("articleId")
        mViewModel.getPriseArticleList(articleId!!, HomeApi.PRISE_ARTICLE_URL).observe(this,{
            mAdapter.setNewData(it)
        })
    }

    companion object {
        fun launch(activity: FragmentActivity, articleId:String) =
            activity.apply {
                val intent = Intent(this, PriseListActivity::class.java)
                intent.putExtra("articleId",articleId)
                startActivity(intent)
            }
    }
}