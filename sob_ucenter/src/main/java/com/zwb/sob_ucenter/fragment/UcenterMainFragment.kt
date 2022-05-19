package com.zwb.sob_ucenter.fragment

import android.annotation.SuppressLint
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.scwang.smartrefresh.layout.header.ClassicsHeader
import com.youth.banner.util.BannerUtils
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.mvvm.v.BaseFragment
import com.zwb.lib_base.utils.EventBusRegister
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.bean.UserBean
import com.zwb.lib_common.constant.Constants
import com.zwb.lib_common.constant.SpKey
import com.zwb.lib_common.event.StringEvent
import com.zwb.lib_common.service.ucenter.wrap.UcenterServiceWrap
import com.zwb.sob_ucenter.R
import com.zwb.sob_ucenter.UcenterApi
import com.zwb.sob_ucenter.UcenterViewModel
import com.zwb.sob_ucenter.activity.SettingActivity
import com.zwb.sob_ucenter.bean.AchievementBean
import com.zwb.sob_ucenter.bean.MsgCountBean
import com.zwb.sob_ucenter.databinding.UcenterFragmentMainBinding
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@EventBusRegister
class UcenterMainFragment : BaseFragment<UcenterFragmentMainBinding, UcenterViewModel>() {
    override val mViewModel by viewModels<UcenterViewModel>()

    private var user: UserBean? = null

    override fun UcenterFragmentMainBinding.initView() {
        StatusBarUtil.setPaddingSmart(mContext, this.toolbar)
        setUserData(
            SpUtils.getString(SpKey.USER_AVATAR, ""),
            SpUtils.getString(SpKey.USER_NICKNAME, "")
        )
        mBinding.refreshLayout.setRefreshHeader(ClassicsHeader(requireContext()))
        initListener()
    }

    private fun initListener() {
        mBinding.refreshLayout.setOnRefreshListener {
            initRequestData()
            mBinding.refreshLayout.finishRefresh()
        }
        mBinding.ivSetting.setOnClickListener {
            SettingActivity.launch(requireActivity())
        }

        mBinding.ivNotify.setOnClickListener {
            UcenterServiceWrap.instance.launchMassage()
        }

        mBinding.ivAvatar.setOnClickListener {
            user?.let {
                UcenterServiceWrap.instance.launchDetail(it.userId)
            }
        }

        mBinding.tvStars.setOnClickListener {
            user?.let {
//                UcenterServiceWrap.instance.launchUcenterList("我的点赞",it.userId)
            }
        }

        mBinding.tvFollow.setOnClickListener {
            user?.let {
                UcenterServiceWrap.instance.launchUcenterList(
                    Constants.Ucenter.PAGE_FOLLOW,
                    it.userId
                )
            }
        }

        mBinding.tvCollection.setOnClickListener {
            user?.let {
                UcenterServiceWrap.instance.launchUcenterList(
                    Constants.Ucenter.PAGE_COLLOCATION,
                    it.userId
                )
            }
        }

        mBinding.tvFans.setOnClickListener {
            user?.let {
                UcenterServiceWrap.instance.launchUcenterList(
                    Constants.Ucenter.PAGE_FANS,
                    it.userId
                )
            }
        }

        mBinding.tvRanking.setOnClickListener {
            user?.let {
                UcenterServiceWrap.instance.launchUcenterList(
                    Constants.Ucenter.PAGE_RANKING,
                    it.userId
                )
            }
        }

        mBinding.tvSob.setOnClickListener {
            user?.let {
                UcenterServiceWrap.instance.launchUcenterList(
                    Constants.Ucenter.PAGE_SOB,
                    it.userId
                )
            }
        }
        mBinding.tvSobNum.setOnClickListener {
            user?.let {
                UcenterServiceWrap.instance.launchUcenterList(
                    Constants.Ucenter.PAGE_SOB,
                    it.userId
                )
            }
        }
    }

    override fun initObserve() {
    }

    override fun initRequestData() {
        mViewModel.getUserInfo(key = UcenterApi.USER_INFO_URL).observe(viewLifecycleOwner, {
            it?.let { user ->
                this.user = user
                setUserData(user.avatar, user.nickname, user.vip)
            }
        })

        mViewModel.getMyAchievement(UcenterApi.USER_ACHIEVEMENT_URL).observe(viewLifecycleOwner, {
            it?.let {
                setAchievementData(it)
            }
        })
        getMsgCount()
    }

    /**
     * 获取消息通知
     */
    private fun getMsgCount(){
        mViewModel.getMsgCount(UcenterApi.USER_MSG_COUNT_URL).observe(viewLifecycleOwner, {
            it?.let {
                setMsgCountData(it)
            }
        })
    }

    private fun setUserData(avatar: String?, nickname: String?, isVip: Boolean = false) {
        avatar?.let {
            mBinding.ivAvatar.loadAvatar(isVip,avatar)
        }
        mBinding.tvNickname.text = nickname
        mBinding.viewVip.setLeftTopTextIsBold(true)
        mBinding.viewVip.setLeftBottomTextIsBold(true)
        if (isVip) {
            mBinding.viewVip.setLeftTopString("VIP会员")
            mBinding.viewVip.setLeftBottomString("感谢您一起建设阳光沙滩")
            mBinding.viewVip.leftTopTextView.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.colorVip2
                )
            )
        } else {
            mBinding.viewVip.setLeftTopString("普通会员")
            mBinding.viewVip.setLeftBottomString("升级为VIP会员权益多多")
            mBinding.viewVip.setLeftTopTextColor(R.color.grey_light)
        }
    }

    private fun setMsgCountData(msg: MsgCountBean) {
        val total =
            msg.articleMsgCount + msg.atMsgCount + msg.momentCommentCount + msg.shareMsgCount + msg.systemMsgCount + msg.thumbUpMsgCount + msg.wendaMsgCount
        if (total == 0) {
            mBinding.tvNotifyNum.gone()
        } else {
            mBinding.tvNotifyNum.visible()
            mBinding.tvNotifyNum.text = if (total > 99) "99" else total.toString()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setAchievementData(achieve: AchievementBean) {
        mBinding.tvHeaderSob.text = resources.getString(R.string.common_sunof_coin) + achieve.sob

        mBinding.tvStars.setCenterTopString(achieve.thumbUpTotal.toString())
        mBinding.tvStars.setCenterTopTextIsBold(true)

        mBinding.tvFollow.setCenterTopString(achieve.followCount.toString())
        mBinding.tvFollow.setCenterTopTextIsBold(true)

        mBinding.tvCollection.setCenterTopString(achieve.favorites.toString())
        mBinding.tvCollection.setCenterTopTextIsBold(true)

        mBinding.tvFans.setCenterTopString(achieve.fansCount.toString())
        mBinding.tvFans.setCenterTopTextIsBold(true)

        mBinding.tvViewNum.text = achieve.atotalView.toString()
        val dxView = "昨日新增 ${achieve.articleDxView}"
        mBinding.tvViewDx.text = UIUtils.setTextViewContentStyle(
            dxView,
            AbsoluteSizeSpan(UIUtils.dp2px(14f)),
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorPrimary)),
            5, dxView.length
        )

        mBinding.tvGetStarNum.text = achieve.thumbUpTotal.toString()
        val thumbUpDx = "昨日新增 ${achieve.thumbUpDx}"
        mBinding.tvGetStarDx.text = UIUtils.setTextViewContentStyle(
            thumbUpDx,
            AbsoluteSizeSpan(UIUtils.dp2px(14f)),
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorPrimary)),
            5, thumbUpDx.length
        )

        mBinding.tvSobNum.text = achieve.sob.toString()
        val sobDx = "昨日新增 ${achieve.sobDx}"
        mBinding.tvSobDx.text = UIUtils.setTextViewContentStyle(
            sobDx,
            AbsoluteSizeSpan(UIUtils.dp2px(14f)),
            ForegroundColorSpan(ContextCompat.getColor(requireContext(), R.color.colorPrimary)),
            5, sobDx.length
        )
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onEventMsgRead(event: StringEvent){
        when (event.event) {
            StringEvent.Event.MSG_READ -> {
                getMsgCount()
            }
        }
    }
}