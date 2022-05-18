package com.zwb.sob_ucenter.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.Constants.Ucenter.PAGE_COLLOCATION
import com.zwb.lib_common.constant.Constants.Ucenter.PAGE_FANS
import com.zwb.lib_common.constant.Constants.Ucenter.PAGE_FOLLOW
import com.zwb.lib_common.constant.Constants.Ucenter.PAGE_RANKING
import com.zwb.lib_common.constant.Constants.Ucenter.PAGE_SOB
import com.zwb.lib_common.constant.RoutePath
import com.zwb.sob_ucenter.R
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.databinding.UcenterActivityListBinding
import com.zwb.sob_ucenter.fragment.UserCollectionFragment
import com.zwb.sob_ucenter.fragment.UserFollowFragment
import com.zwb.sob_ucenter.fragment.UserRankingFragment
import com.zwb.sob_ucenter.fragment.UserSobFragment

@Route(path = RoutePath.Ucenter.PAGE_UCENTER_LIST)
class UcenterListActivity : BaseActivity<UcenterActivityListBinding, UcenterViewModel>() {

    @JvmField
    @Autowired(name = Constants.Ucenter.PAGE_TYPE)
    var pageType: Int = 0

    @Autowired
    lateinit var userId: String

    override val mViewModel by viewModels<UcenterViewModel>()

    override fun UcenterActivityListBinding.initView() {

        this.includeBar.ivBack.setOnClickListener { finish() }

        val transaction = supportFragmentManager.beginTransaction()
        val fragment: Fragment
        when (pageType) {
            PAGE_COLLOCATION -> {
                mBinding.includeBar.tvTitle.text = "收藏集"
                fragment = UserCollectionFragment()
                val bundle = Bundle()
                bundle.putString("userId", userId)
                fragment.arguments = bundle
                transaction.add(R.id.fl_content, fragment).commit()
            }
            PAGE_FOLLOW, PAGE_FANS -> {
                mBinding.includeBar.tvTitle.text = if (pageType == PAGE_FOLLOW) "关注列表" else "粉丝列表"
                fragment = UserFollowFragment()
                val bundle = Bundle()
                bundle.putInt(Constants.Ucenter.PAGE_TYPE, pageType)
                bundle.putString("userId", userId)
                fragment.arguments = bundle
                transaction.add(R.id.fl_content, fragment).commit()
            }
            PAGE_RANKING -> {
                mBinding.includeBar.tvTitle.text = "富豪榜"
                fragment = UserRankingFragment()
                transaction.add(R.id.fl_content, fragment).commit()
            }
            PAGE_SOB -> {
                mBinding.includeBar.tvTitle.text = "Sob 币"
                fragment = UserSobFragment()
                val bundle = Bundle()
                bundle.putString("userId", userId)
                fragment.arguments = bundle
                transaction.add(R.id.fl_content, fragment).commit()
            }
        }

    }

    override fun setStatusBar() {
        super.setStatusBar()
        StatusBarUtil.setPaddingSmart(this, mBinding.includeBar.toolbar)
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }
}