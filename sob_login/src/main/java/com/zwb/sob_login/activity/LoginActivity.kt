package com.zwb.sob_login.activity

import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.util.SparseArray
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.EventBusRegister
import com.zwb.lib_base.utils.EventBusUtils
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.event.StringEvent
import com.zwb.sob_login.LoginViewModel
import com.zwb.sob_login.R
import com.zwb.sob_login.databinding.LoginActivityBinding
import com.zwb.sob_login.fragment.ForgetFragment
import com.zwb.sob_login.fragment.LoginFragment
import com.zwb.sob_login.fragment.RegisterFragment
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@EventBusRegister
@Route(path = RoutePath.Login.PAGE_LOGIN)
class LoginActivity : BaseActivity<LoginActivityBinding, LoginViewModel>() {

    override val mViewModel by viewModels<LoginViewModel>()

    @JvmField
    @Autowired
    var path: String? = null

    private var mLastIndex: Int = -1
    private val mFragmentSparseArray = SparseArray<Fragment>()

    // 当前显示的 fragment
    private var mCurrentFragment: Fragment? = null
    private var mLastFragment: Fragment? = null


    override fun LoginActivityBinding.initView() {
        switchFragment(PageType.SWITCH_LOGIN.ordinal)
        switchStyle(PageType.SWITCH_LOGIN.ordinal)
        this.ivClose.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        when (mLastIndex) {
            PageType.SWITCH_LOGIN.ordinal -> {
                finish()
            }
            PageType.SWITCH_REGISTER.ordinal -> {
                switchFragment(PageType.SWITCH_LOGIN.ordinal)
                switchStyle(mLastIndex)
            }
            PageType.SWITCH_FORGET.ordinal -> {
                switchFragment(PageType.SWITCH_LOGIN.ordinal)
                switchStyle(mLastIndex)
            }
        }
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
    }

    private fun switchFragment(index: Int) {
        val fragmentManager = supportFragmentManager
        val transaction = fragmentManager.beginTransaction()
        // 将当前显示的fragment和上一个需要隐藏的fragment分别加上tag, 并获取出来
        // 给fragment添加tag,这样可以通过findFragmentByTag找到存在的fragment，不会出现重复添加
        mCurrentFragment = fragmentManager.findFragmentByTag(index.toString())
        mLastFragment = fragmentManager.findFragmentByTag(mLastIndex.toString())
        // 如果位置不同
        if (index != mLastIndex) {
            if (mLastFragment != null) {
                transaction.hide(mLastFragment!!)
            }
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.layout, mCurrentFragment!!, index.toString())
            } else {
                transaction.show(mCurrentFragment!!)
            }
        }

        // 如果位置相同或者新启动的应用
        if (index == mLastIndex) {
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.layout, mCurrentFragment!!, index.toString())
            }
        }
        transaction.commit()
        mLastIndex = index
    }

    private fun getFragment(index: Int): Fragment {
        var fragment: Fragment? = mFragmentSparseArray.get(index)
        if (fragment == null) {
            when (index) {
                PageType.SWITCH_LOGIN.ordinal -> fragment = LoginFragment()
                PageType.SWITCH_REGISTER.ordinal -> fragment = RegisterFragment()
                PageType.SWITCH_FORGET.ordinal -> fragment = ForgetFragment()
            }
            mFragmentSparseArray.put(index, fragment)
        }
        return fragment!!
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEvent(event: PageType) {
        Log.e("111111111111111111", event.name)
        when (event) {
            PageType.SWITCH_REGISTER -> {
                switchFragment(PageType.SWITCH_REGISTER.ordinal)
                switchStyle(PageType.SWITCH_REGISTER.ordinal)
            }
            PageType.SWITCH_FORGET -> {
                switchFragment(PageType.SWITCH_FORGET.ordinal)
                switchStyle(PageType.SWITCH_FORGET.ordinal)
            }
            PageType.SWITCH_LOGIN -> {
                switchFragment(PageType.SWITCH_LOGIN.ordinal)
                switchStyle(PageType.SWITCH_LOGIN.ordinal)
            }
            PageType.LOGIN_SUCCESS -> {
                onLoginSuccess()
            }
        }
    }

    private fun switchStyle(i: Int) {
        when (i) {
            PageType.SWITCH_REGISTER.ordinal -> {
                val str = resources.getString(R.string.login_register_agreement)
                mBinding.tvAgreement.text = UIUtils.setTextViewContentStyle(
                    str,
                    AbsoluteSizeSpan(UIUtils.dp2px(14f)),
                    ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)),
                    str.indexOf("《"), str.length
                )
                mBinding.tvLogo.text = "注册"
            }
            PageType.SWITCH_FORGET.ordinal -> {
                mBinding.tvAgreement.setText(R.string.common_website)
                mBinding.tvLogo.text = "找回密码"
            }
            PageType.SWITCH_LOGIN.ordinal -> {
                val str = resources.getString(R.string.login_agreement)
                mBinding.tvAgreement.text = UIUtils.setTextViewContentStyle(
                    str,
                    AbsoluteSizeSpan(UIUtils.dp2px(14f)),
                    ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorPrimary)),
                    str.indexOf("《"), str.length
                )
                mBinding.tvLogo.text = "登录"
            }
        }
    }


    private fun onLoginSuccess(){
        if (!TextUtils.isEmpty(path)) {
            when(path){
                RoutePath.Ucenter.FRAGMENT_UCENTER -> EventBusUtils.postEvent(StringEvent(StringEvent.Event.SWITCH_UCENTER))
                else -> ARouter.getInstance().build(path)
                    .with(intent.extras)
                    .navigation()
            }
        }
        finish()
    }

    override fun onDestroy() {
        super.onDestroy()
        mFragmentSparseArray.clear()
    }

    enum class PageType(v: Int) {
        SWITCH_LOGIN(0), SWITCH_REGISTER(1), SWITCH_FORGET(2), LOGIN_SUCCESS(3)
    }
}
