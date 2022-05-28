package com.zwb.sob_shop.activity

import android.content.Intent
import androidx.activity.viewModels
import androidx.fragment.app.FragmentActivity
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.sob_shop.ShopViewModel
import com.zwb.sob_shop.databinding.ShopActivitySearchBinding

class GoodsSearchActivity: BaseActivity<ShopActivitySearchBinding, ShopViewModel>() {

    override val mViewModel by viewModels<ShopViewModel>()

    override fun ShopActivitySearchBinding.initView() {

    }

    override fun initObserve() {

    }

    override fun initRequestData() {

    }

    companion object {
        fun launch(activity: FragmentActivity) =
            activity.apply {
                val intent = Intent(this, GoodsSearchActivity::class.java)
                startActivity(intent)
            }
    }
}