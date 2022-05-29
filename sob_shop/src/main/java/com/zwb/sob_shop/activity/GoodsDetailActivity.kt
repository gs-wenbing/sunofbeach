package com.zwb.sob_shop.activity

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.youth.banner.indicator.CircleIndicator
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_base.utils.UIUtils
import com.zwb.lib_common.view.CommonViewUtils
import com.zwb.sob_shop.R
import com.zwb.sob_shop.ShopApi
import com.zwb.sob_shop.ShopViewModel
import com.zwb.sob_shop.adapter.ShopBannerAdapter
import com.zwb.sob_shop.adapter.ShopDetailAdapter
import com.zwb.sob_shop.bean.*
import com.zwb.sob_shop.databinding.ShopActivityDetailBinding
import com.zwb.sob_shop.databinding.ShopDetailHeaderBinding
import com.zwb.sob_shop.view.CopyCouponDialog

class GoodsDetailActivity : BaseActivity<ShopActivityDetailBinding, ShopViewModel>() {

    override val mViewModel by viewModels<ShopViewModel>()

    private var goods: ShopItemGoodsBean? = null

    private lateinit var mAdapter: ShopDetailAdapter

    lateinit var headerBinding: ShopDetailHeaderBinding

    private lateinit var couponDialog: CopyCouponDialog

    override fun ShopActivityDetailBinding.initView() {
        intent.getParcelableExtra<ShopItemGoodsBean>("goods").also { goods = it }
        mBinding.includeBar.tvTitle.text = "领优惠券"
        mBinding.includeBar.ivRight.gone()
        mBinding.includeBar.ivBack.setOnClickListener { finish() }

        mAdapter = ShopDetailAdapter(mutableListOf())
        headerBinding = ShopDetailHeaderBinding.inflate(layoutInflater)
        mAdapter.addHeaderView(headerBinding.root)

        this.rvList.setHasFixedSize(true)
        this.rvList.layoutManager = LinearLayoutManager(this@GoodsDetailActivity)
        this.rvList.adapter = mAdapter

        mAdapter.setOnItemClickListener { adapter, view, position ->

        }

        couponDialog = CopyCouponDialog(this@GoodsDetailActivity)
        couponDialog.setBtnText(if (isTBInstallExist()) "打开淘宝" else "去分享")
        couponDialog.onOpenTaobao {
            if (isTBInstallExist()) {
                val intent = packageManager.getLaunchIntentForPackage("com.taobao.taobao")
                intent?.let {
                    intent.action = "Android.intent.action.VIEW"
                    intent.setClassName("com.taobao.taobao", "com.taobao.tao.TBMainActivity")
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)
                }
                couponDialog.dismiss()
            } else {
                toast("去分享")
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
        if (goods != null) {
            setViewData(goods as IGoodsItem)
            mViewModel.goodsRelativeList(
                (goods as IGoodsItem).getGoodsId(),
                ShopApi.GOODS_RELATIVE_URL
            ).observe(this, {
                it?.let {
                    mAdapter.setNewData(it.tbk_item_recommend_get_response?.results?.n_tbk_item)
                }
                if (mAdapter.data.size == 0) {
                    headerBinding.tvRelative.text = "暂无宝贝推荐"
                } else {
                    headerBinding.tvRelative.text = "更多宝贝推荐"
                }
            })
            mViewModel.tpwd(TpwdInputBean("https:${(goods as IGoodsItem).getCouponUrl()}"))
                .observe(this, {
                    if (it.success) {
                        it.data?.let { res ->
                            couponDialog.setTpwdValue(res.tbk_tpwd_create_response.data.model)
                        }
                    }
                })
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setViewData(item: IGoodsItem) {
        headerBinding.includeBanner.tvGoodsTitle.text = item.getGoodsTitle()
        headerBinding.includeBanner.tvPrice.text = "现价 ￥${item.getOriginPrice()}"
        headerBinding.includeBanner.tvCouponPrice.text =
            "券后价 ￥${String.format("%.2f", (item.getOriginPrice() - item.getCouponAmount()))}"
        headerBinding.includeBanner.tvSaleNum.text = "${item.getSaleNum()} • 人已购买"

        val bannerAdapter = ShopBannerAdapter(mutableListOf())
        headerBinding.includeBanner.banner.adapter = bannerAdapter
        headerBinding.includeBanner.banner.addBannerLifecycleObserver(this)
        bannerAdapter.setDatas(item.getSmallImages())
        bannerAdapter.setOnBannerListener { _, position ->
            CommonViewUtils.showBigImage(this, item.getSmallImages(), position)
        }

        headerBinding.includeBanner.banner.indicator = CircleIndicator(this)
        headerBinding.includeBanner.banner.setIndicatorSelectedColor(
            ContextCompat.getColor(
                this,
                R.color.colorWarning
            )
        )

        headerBinding.tvCouponAmt.text = "￥ ${item.getCouponAmount()}"

        headerBinding.tvBigPrice.text = "日常价\n￥${item.getOriginPrice()}"
        val couponAmountStr = "领券立减\n ${item.getCouponAmount()}元"
        headerBinding.tvBigCouponAmt.text =
            UIUtils.setTextViewContentStyle(
                couponAmountStr,
                AbsoluteSizeSpan(UIUtils.sp2px(20f)),
                ForegroundColorSpan(ContextCompat.getColor(this, R.color.white)),
                4, couponAmountStr.length - 1
            )
        val bigCouponPriceStr =
            "￥${String.format("%.2f", (item.getOriginPrice() - item.getCouponAmount()))}"
        headerBinding.tvBigCouponPrice.text = UIUtils.setTextViewContentStyle(
            bigCouponPriceStr,
            AbsoluteSizeSpan(UIUtils.sp2px(24f)),
            ForegroundColorSpan(ContextCompat.getColor(this, R.color.colorWarning)),
            1, bigCouponPriceStr.indexOf(".")
        )

        headerBinding.tvGetCoupon.setOnClickListener {
            showCouponDialog()
        }
        headerBinding.btnCouponBuy.setOnClickListener {
            showCouponDialog()
        }
    }

    private fun showCouponDialog() {
        val cm = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        //复制到粘贴板
        val clipData = ClipData.newPlainText("tpwd_code", couponDialog.getTpwdValue())
        cm.setPrimaryClip(clipData)
        couponDialog.show()
    }

    private fun isTBInstallExist(): Boolean {
        val pm = this.packageManager
        return try {
            pm.getPackageInfo("com.taobao.taobao", PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    companion object {
        fun launch(activity: FragmentActivity, goods: ShopItemGoodsBean) =
            activity.apply {
                val intent = Intent(this, GoodsDetailActivity::class.java)
                intent.putExtra("goods", goods)
                startActivity(intent)
            }
    }
}