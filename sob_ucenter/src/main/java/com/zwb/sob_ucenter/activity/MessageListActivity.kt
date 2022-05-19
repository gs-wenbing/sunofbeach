package com.zwb.sob_ucenter.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.Constants.Ucenter.PAGE_MSG_ARTICLE
import com.zwb.lib_common.constant.Constants.Ucenter.PAGE_MSG_DYNAMIC
import com.zwb.lib_common.constant.Constants.Ucenter.PAGE_MSG_STAR
import com.zwb.lib_common.constant.Constants.Ucenter.PAGE_MSG_SYSTEM
import com.zwb.lib_common.constant.Constants.Ucenter.PAGE_MSG_WENDA
import com.zwb.lib_common.constant.RoutePath
import com.zwb.sob_ucenter.R
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.databinding.UcenterActivityListBinding
import com.zwb.sob_ucenter.fragment.*

@Route(path = RoutePath.Ucenter.PAGE_MSG_LIST)
class MessageListActivity : BaseActivity<UcenterActivityListBinding, UcenterViewModel>() {

    @JvmField
    @Autowired(name = Constants.Ucenter.PAGE_TYPE)
    var pageType: Int = 0

    @Autowired
    lateinit var title: String

    override val mViewModel by viewModels<UcenterViewModel>()

    override fun UcenterActivityListBinding.initView() {

        mBinding.includeBar.tvTitle.text = title
        this.includeBar.ivBack.setOnClickListener { finish() }

        val transaction = supportFragmentManager.beginTransaction()
        when(pageType){
            PAGE_MSG_SYSTEM->{
                // 系统消息
                transaction.add(R.id.fl_content, UserMessageSystemFragment()).commit()
            }
            else->{
                val  fragment = UserMessageListFragment()
                val bundle = Bundle()
                bundle.putInt("pageType", pageType)
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