package com.zwb.lib_common.view

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import com.tencent.smtt.sdk.WebChromeClient
import com.tencent.smtt.sdk.WebSettings
import com.tencent.smtt.sdk.WebView
import com.tencent.smtt.sdk.WebViewClient
import com.zwb.lib_base.ktx.gone
import com.zwb.lib_base.ktx.visible
import com.zwb.lib_common.R


class X5WebView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : WebView(context, attrs) {
    private val TAG = "x5"
    private val MAX_LENGTH = 8

    var progressBar: ProgressBar? = null

    init {
        isHorizontalScrollBarEnabled = false;//水平不显示小方块
        isVerticalScrollBarEnabled = false; //垂直不显示小方块

        scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY;//滚动条在WebView内侧显示

        progressBar = ProgressBar(context, null, android.R.attr.progressBarStyleHorizontal)
        progressBar!!.max = 100;
        progressBar!!.progressDrawable =
            ContextCompat.getDrawable(context, R.drawable.progress_bar_states)

        addView(
            progressBar, LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                8
            )
        )
        initWebViewSettings()
    }

    private fun initWebViewSettings() {
        setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))
        webViewClient = client
        webChromeClient = chromeClient
        isClickable = true

        val webSetting = settings
        webSetting.javaScriptEnabled = true
        webSetting.builtInZoomControls = true;
        webSetting.javaScriptCanOpenWindowsAutomatically = true;
        webSetting.domStorageEnabled = true;
        webSetting.allowFileAccess = true;
        webSetting.layoutAlgorithm = WebSettings.LayoutAlgorithm.NARROW_COLUMNS;
        webSetting.setSupportZoom(true);
        webSetting.useWideViewPort = true;
        webSetting.setSupportMultipleWindows(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        webSetting.pluginState = WebSettings.PluginState.ON_DEMAND;
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
    }

    fun handleKeyEvent(keyCode: Int, keyEvent: KeyEvent?): Boolean {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            return false
        }
        if (canGoBack()) {
            goBack()
            return true
        }
        return false
    }

    private val client: WebViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(p0: WebView?, p1: String?): Boolean {
            //这里可以对特殊scheme进行拦截处理
            return true;//要返回true否则内核会继续处理
        }
    }

    private val chromeClient: WebChromeClient = object : WebChromeClient() {

        override fun onProgressChanged(p0: WebView?, newProgress: Int) {
            progressBar?.apply {
                progress = newProgress
                if (newProgress != 100) {
                    //WebView加载没有完成 就显示我们自定义的加载图
                    visible()
                } else {
                    //WebView加载完成 就隐藏进度条,显示WebView
                    gone()
                }
            }

        }

        override fun onReceivedTitle(p0: WebView?, title: String?) {
            if (TextUtils.isEmpty(title)) {
                return;
            }
            if (title != null && title.length > MAX_LENGTH) {
                Log.e("=========", "${title.subSequence(0, MAX_LENGTH)}...")
            } else {
                Log.e("=========", title)
            }
        }

    }
}