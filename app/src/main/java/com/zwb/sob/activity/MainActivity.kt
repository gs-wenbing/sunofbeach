package com.zwb.sob.activity

import android.content.Intent
import android.util.SparseArray
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.pgyer.pgyersdk.PgyerSDKManager
import com.pgyer.pgyersdk.callback.CheckoutCallBack
import com.pgyer.pgyersdk.model.CheckSoftModel
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.EventBusRegister
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.constant.SpKey
import com.zwb.lib_common.event.StringEvent
import com.zwb.lib_common.service.home.wrap.HomeServiceWrap
import com.zwb.lib_common.service.moyu.wrap.MoyuServiceWrap
import com.zwb.lib_common.service.shop.wrap.ShopServiceWrap
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.lib_common.service.wenda.wrap.WendaServiceWrap
import com.zwb.sob.MainViewModel
import com.zwb.sob.R
import com.zwb.sob.databinding.ActivityMainBinding
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@EventBusRegister
class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {
    override val mViewModel by viewModels<MainViewModel>()

    private var mLastIndex: Int = -1
    private val mFragmentSparseArray = SparseArray<Fragment>()
    // 当前显示的 fragment
    private var mCurrentFragment: Fragment? = null
    private var mLastFragment: Fragment? = null

    override fun ActivityMainBinding.initView() {
        switchFragment(HOME)
        initBottomNavigation()
}
    private fun initBottomNavigation() {
        mBinding.navView.setOnNavigationItemSelectedListener { menuItem: MenuItem ->
            when (menuItem.itemId) {
                R.id.menu_home -> {
                    switchFragment(HOME)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_moyu -> {
                    switchFragment(MOYU)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_wenda -> {
                    switchFragment(WENDA)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_shop -> {
                    switchFragment(SHOP)
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.menu_ucenter -> {
                    val isLogin = SpUtils.getBoolean(SpKey.IS_LOGIN, false)
                    if(isLogin == true){
                        switchFragment(UCENTER)
                        return@setOnNavigationItemSelectedListener true
                    }else{
                        ARouter.getInstance()
                            .build(RoutePath.Login.PAGE_LOGIN)
                            .withString(RoutePath.PATH, RoutePath.Ucenter.FRAGMENT_UCENTER)
                            .navigation()
                        return@setOnNavigationItemSelectedListener false
                    }
                }
                else -> return@setOnNavigationItemSelectedListener false
            }
        }
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
                transaction.add(R.id.content, mCurrentFragment!!, index.toString())
            } else {
                transaction.show(mCurrentFragment!!)
            }
        }

        // 如果位置相同或者新启动的应用
        if (index == mLastIndex) {
            if (mCurrentFragment == null) {
                mCurrentFragment = getFragment(index)
                transaction.add(R.id.content, mCurrentFragment!!, index.toString())
            }
        }
        transaction.commitAllowingStateLoss()
        mLastIndex = index
    }

    private fun getFragment(index: Int): Fragment {
        var fragment: Fragment? = mFragmentSparseArray.get(index)
        if (fragment == null) {
            when (index) {
                HOME -> fragment = HomeServiceWrap.instance.getFragment()
                MOYU -> fragment = MoyuServiceWrap.instance.getFragment()
                WENDA -> fragment = WendaServiceWrap.instance.getFragment()
                SHOP -> fragment = ShopServiceWrap.instance.getFragment()
                UCENTER -> fragment = UcenterServiceWrap.instance.getFragment()
            }
            mFragmentSparseArray.put(index, fragment)
        }
        return fragment!!
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventSwitchTab(event: StringEvent){
        when (event.event) {
            StringEvent.Event.SWITCH_HOME -> mBinding.navView.selectedItemId = R.id.menu_home
            StringEvent.Event.SWITCH_UCENTER -> mBinding.navView.selectedItemId = R.id.menu_ucenter
        }
    }

    override fun initObserve() {
        PgyerSDKManager.checkVersionUpdate(this, object : CheckoutCallBack {
            override fun onNewVersionExist(model: CheckSoftModel) {
                //检查版本成功（有新版本）
                /**
                 *   CheckSoftModel 参数介绍
                 *
                 *    private int buildBuildVersion;//蒲公英生成的用于区分历史版本的build号
                 *     private String forceUpdateVersion;//强制更新版本号（未设置强置更新默认为空）
                 *     private String forceUpdateVersionNo;//强制更新的版本编号
                 *     private boolean needForceUpdate;//	是否强制更新
                 *     private boolean buildHaveNewVersion;//是否有新版本
                 *     private String downloadURL;//应用安装地址
                 *     private String buildVersionNo;//上传包的版本编号，默认为1 (即编译的版本号，一般来说，编译一次会
                 *    变动一次这个版本号, 在 Android 上叫 Version Code。对于 iOS 来说，是字符串类型；对于 Android 来
                 *    说是一个整数。例如：1001，28等。)
                 *     private String buildVersion;//版本号, 默认为1.0 (是应用向用户宣传时候用到的标识，例如：1.1、8.2.1等。)
                 *     private String buildShortcutUrl;//	应用短链接
                 *     private String buildUpdateDescription;//	应用更新说明
                 */
                toast("有新版本")
            }

            override fun onNonentityVersionExist(error: String) {
                //无新版本
//                toast("无新版本")
            }

            override fun onFail(error: String) {
                //请求异常
//                toast("请求异常")
            }
        })
//        PgyerSDKManager.checkSoftwareUpdate(this)
    }

    override fun initRequestData() {
    }

    companion object {
        const val HOME = 0
        const val MOYU = 1
        const val WENDA = 2
        const val SHOP = 3
        const val UCENTER = 4
        fun launch(activity: FragmentActivity) =
            activity.apply {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
    }

}