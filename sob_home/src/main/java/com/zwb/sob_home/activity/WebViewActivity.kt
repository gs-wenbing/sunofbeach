package com.zwb.sob_home.activity

import android.os.Build
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import androidx.activity.viewModels
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_base.mvvm.v.BaseActivity
import com.zwb.lib_base.utils.StatusBarUtil
import com.zwb.lib_common.constant.RoutePath
import com.zwb.sob_home.HomeViewModel
import com.zwb.sob_home.databinding.HomeActivityWebviewBinding

@Route(path = RoutePath.Home.PAGE_WEBVIEW)
class WebViewActivity : BaseActivity<HomeActivityWebviewBinding, HomeViewModel>() {

    override val mViewModel by viewModels<HomeViewModel>()

    @JvmField
    @Autowired
    var title: String? = null

    @Autowired
    lateinit var url: String

    override fun HomeActivityWebviewBinding.initView() {
        initX5WebView()
        mBinding.includeBar.ivBack.setOnClickListener { finish() }
    }

    private fun initX5WebView() {
        val webSetting = mBinding.x5webview.settings
        webSetting.javaScriptEnabled = true
        webSetting.builtInZoomControls = true;
        webSetting.javaScriptCanOpenWindowsAutomatically = true;
        webSetting.domStorageEnabled = true;
        webSetting.allowFileAccess = true;
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.SINGLE_COLUMN
        webSetting.useWideViewPort = true
        webSetting.loadWithOverviewMode = true
        webSetting.setSupportMultipleWindows(true)

        webSetting.setAppCacheEnabled(true)
        webSetting.setGeolocationEnabled(true)
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE)
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
            WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED
        )
        mBinding.x5webview.loadUrl(url)
        mBinding.x5webview.webViewClient = object : WebViewClient() {

            override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
                //这里可以对特殊scheme进行拦截处理
                return true;//要返回true否则内核会继续处理
            }
        }
        mBinding.x5webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(p0: WebView?, newProgress: Int) {
                if (newProgress < 100) {
                    //WebView加载没有完成 就显示我们自定义的加载图
                    mBinding.progressBar.progress = newProgress
                    mBinding.progressBar.visible()
                } else {
                    //WebView加载完成 就隐藏进度条,显示WebView
                    mBinding.progressBar.gone()
                }
            }
            override fun onReceivedTitle(p0: WebView?, title: String?) {
                if (TextUtils.isEmpty(title)) {
                    return
                }
                mBinding.includeBar.tvTitle.text = title!!
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

//    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
//        return mBinding.x5webview.handleKeyEvent(keyCode,event)
//    }

    override fun onDestroy() {
        mBinding.x5webview.destroy()
        super.onDestroy()
    }
}