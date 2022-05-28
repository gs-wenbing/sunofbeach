package com.zwb.sob_ucenter.activity

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.reduceDragSensitivity
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.bean.UserBean
import com.zwb.lib_common.constant.RoutePath
import com.zwb.lib_common.constant.SpKey
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.sob_ucenter.R
import com.zwb.sob_ucenter.UcenterApi
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.bean.AchievementBean
import com.zwb.sob_ucenter.databinding.UcenterActivityUserCenterBinding
import com.zwb.sob_ucenter.fragment.UserCenterArticleFragment
import com.zwb.sob_ucenter.fragment.UserCenterArticleFragment.Companion.DATA_ARTICLE
import com.zwb.sob_ucenter.fragment.UserCenterArticleFragment.Companion.DATA_SHARE
import com.zwb.sob_ucenter.fragment.UserCenterArticleFragment.Companion.DATA_TYPE
import com.zwb.sob_ucenter.fragment.UserCenterArticleFragment.Companion.DATA_WENDA
import com.zwb.sob_ucenter.fragment.UserCenterMoyuFragment
import com.zwb.sob_ucenter.view.MyCoordinatorLayout
import kotlin.math.abs
import kotlin.math.min

@Route(path = RoutePath.Ucenter.PAGE_UCENTER)
class UserCenterActivity : BaseActivity<UcenterActivityUserCenterBinding, UcenterViewModel>() {

    override val mViewModel by viewModels<UcenterViewModel>()

    @Autowired
    lateinit var userId: String

    private var titles = mutableListOf<String>()
    private val fragments: MutableList<Class<*>> = mutableListOf()

    private lateinit var mAdapter: FragmentStateAdapter

    @RequiresApi(Build.VERSION_CODES.M)
    override fun UcenterActivityUserCenterBinding.initView() {
        val uId = SpUtils.getString(SpKey.USER_ID, "")
        if(userId == uId){
            mBinding.btnToolbarFollow.gone()
            mBinding.btnFollow.text = "编辑"
        }
        titles.add("动态")
        titles.add("文章")
        titles.add("分享")
        titles.add("问答")
        fragments.add(UserCenterMoyuFragment::class.java)
        fragments.add(UserCenterArticleFragment::class.java)
        fragments.add(UserCenterArticleFragment::class.java)
        fragments.add(UserCenterArticleFragment::class.java)
        mAdapter = object : FragmentStateAdapter(this@UserCenterActivity) {
            override fun getItemCount(): Int = fragments.size
            override fun createFragment(position: Int): Fragment {
                return try {
                    val fragment = fragments[position].newInstance() as Fragment
                    val bundle = Bundle()
                    bundle.putString("userId", userId)
                    when (position) {
                        1 -> bundle.putString(DATA_TYPE, DATA_ARTICLE)
                        2 -> bundle.putString(DATA_TYPE, DATA_SHARE)
                        3 -> bundle.putString(DATA_TYPE, DATA_WENDA)
                    }
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
        initScroll()
        initListener()
    }

    override fun setStatusBar() {
        super.setStatusBar()
        StatusBarUtil.setPaddingSmart(this, mBinding.toolbar)
    }

    private fun initListener() {
        mBinding.ivBack.setOnClickListener {
            finish()
        }

        mBinding.ivMore.setOnClickListener {
            toast("更多")
        }
        mBinding.ivRewardCode.setOnClickListener {
            toast("打赏码")
        }
        mBinding.btnToolbarFollow.setOnClickListener {
            if(isLoginIntercept(true)){
                follow(userId)
            }
        }
        mBinding.btnFollow.setOnClickListener {
            if(isLoginIntercept(true)){
                follow(userId)
            }
        }
    }

    private fun initScroll() {
        mBinding.toolbar.setBackgroundColor(0)
        mBinding.toolbarLayout.alpha = 0f
        mBinding.ivBigAvatar.post {
            val h = mBinding.ivTopBg.height
            mBinding.coordinator.setOnScrollListener(object : MyCoordinatorLayout.OnScrollListener {
                var color =
                    ContextCompat.getColor(this@UserCenterActivity, R.color.white) and 0x00ffffff

                override fun onScroll(scrollY: Int) {
                    Log.e("scrollY===", abs(scrollY).toString())
                    // 设置标题栏的这一行的 alpha由 0-> 1
                    mBinding.toolbarLayout.alpha = 1f * (abs(scrollY)) / h
                    // 设置大头像这一行的 alpha由 1-> 0
                    val alpha =
                        1f * (mBinding.toolbar.height - abs(scrollY)) / mBinding.toolbar.height
                    mBinding.ivBigAvatar.alpha = alpha
                    mBinding.btnFollow.alpha = alpha
                    mBinding.tvBigNickName.alpha = alpha
                    mBinding.tvPosition.alpha = alpha
                    // 标题栏背景 由透明到白色
                    val min = min(abs(scrollY), h)
                    mBinding.toolbar.setBackgroundColor(255 * min / h shl 24 or color)
                    // 滚到到一定值后 改变标题栏的图标的颜色
                    if (abs(scrollY) < mBinding.toolbar.height) {
                        setIconStyle(true)
                    } else {
                        setIconStyle(false)
                    }
                }
            })
        }

    }

    private fun setIconStyle(isWhite: Boolean) {
        val ivList = arrayOf(mBinding.ivBack, mBinding.ivMore)
        ivList.forEach {
            it.setImageDrawable(
                UIUtils.tintDrawable(
                    it.drawable,
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            this,
                            if (isWhite) R.color.colorAccent else R.color.icon_color
                        )
                    )
                )
            )
        }
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        mViewModel.getUserInfo(userId, UcenterApi.USER_INFO_URL).observe(this, {
            it?.let { user ->
                setUserData(user)
            }
        })
        mViewModel.getUserAchievement(userId,UcenterApi.USER_ACHIEVEMENT_URL).observe(this,{
            it?.let {
                setAchievementData(it)
            }
        })
        getFollowState(userId)
    }

    private fun getFollowState(userId : String){
        mViewModel.followState(userId).observe(this,{
            mBinding.btnFollow.visible()
            mBinding.btnToolbarFollow.visible()
            if(it.success && it.data!=null){
                CommonViewUtils.setFollowState(mBinding.btnFollow,it.data!!)
                CommonViewUtils.setFollowState(mBinding.btnToolbarFollow,it.data!!)
            }
        })
    }

    /**
     * 关注
     */
    private fun follow(userId : String){
        mViewModel.follow(userId).observe(this, {
            getFollowState(userId)
        })
    }

    @SuppressLint("SetTextI18n")
    private fun setUserData(user: UserBean) {
        mBinding.ivBigAvatar.loadAvatar(user.vip,user.avatar)
        mBinding.ivBigAvatar.setOnClickListener {
            CommonViewUtils.showBigImage(it, user.avatar)
        }

        mBinding.ivToolbarAvatar.loadAvatar(user.vip,user.avatar)

        var position = if (TextUtils.isEmpty(user.position)) "" else user.position
        position = if (TextUtils.isEmpty(user.company)) position else "${position}@${user.company}"
        mBinding.tvPosition.text = position
        mBinding.tvTitle.text = user.nickname
        mBinding.tvBigNickName.text = user.nickname
        if(TextUtils.isEmpty(user.sign)){
            mBinding.tvDesc.gone()
        }else{
            mBinding.tvDesc.visible()
            mBinding.tvDesc.text = user.sign
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setAchievementData(achieve: AchievementBean){
        mBinding.tvAchievement.text = "动态 ${achieve.momentCount}      阅读量 ${achieve.atotalView}     文章 ${achieve.articleTotal}      获赞 ${achieve.thumbUpTotal}"
    }
}