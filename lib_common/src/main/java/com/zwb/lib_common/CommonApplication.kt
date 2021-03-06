package com.zwb.lib_common

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.google.auto.service.AutoService
import com.hjq.toast.ToastUtils
import com.kingja.loadsir.core.LoadSir
import com.pgyer.pgyersdk.PgyerSDKManager
import com.tencent.smtt.export.external.TbsCoreSettings
import com.tencent.smtt.sdk.QbSdk
import com.zwb.lib_base.BaseApplication
import com.zwb.lib_base.BuildConfig
import com.zwb.lib_base.app.ApplicationLifecycle
import com.zwb.lib_base.callback.EmptyCallback
import com.zwb.lib_base.callback.ErrorCallback
import com.zwb.lib_base.callback.LoadingCallback
import com.zwb.lib_base.net.RetrofitFactory
import com.zwb.lib_base.utils.ProcessUtils
import com.zwb.lib_base.utils.SpUtils
import com.zwb.lib_base.utils.VersionStatus
import com.zwb.lib_base.utils.network.NetworkStateClient
import net.mikaelzero.mojito.Mojito
import net.mikaelzero.mojito.loader.glide.GlideImageLoader
import net.mikaelzero.mojito.view.sketch.SketchImageLoadFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient

@AutoService(ApplicationLifecycle::class)
class CommonApplication : ApplicationLifecycle {

    companion object {
        // 全局CommonApplication
        @SuppressLint("StaticFieldLeak")
        lateinit var mCommonApplication: CommonApplication
    }

    /**
     * 同[Application.attachBaseContext]
     * @param context Context
     */
    override fun onAttachBaseContext(context: Context) {
        mCommonApplication = this
        initPgyerSdk(BaseApplication.application)
    }

    /**
     * 同[Application.onCreate]
     * @param application Application
     */
    override fun onCreate(application: Application) {}

    /**
     * 同[Application.onTerminate]
     * @param application Application
     */
    override fun onTerminate(application: Application) {}

    /**
     * 主线程前台初始化
     * @return MutableList<() -> String> 初始化方法集合
     */
    override fun initByFrontDesk(): MutableList<() -> String> {
        val list = mutableListOf<() -> String>()
        // 以下只需要在主进程当中初始化 按需要调整
        if (ProcessUtils.isMainProcess(BaseApplication.context)) {
            list.add { initMMKV() }
            list.add { initARouter() }
            list.add { initNetworkStateClient() }
            list.add { initLoadSir() }
            list.add { initMojito() }
            list.add { initToast() }
        }
        list.add { initTencentBugly() }
        return list
    }


    private fun initPgyerSdk(application: Application){
        PgyerSDKManager
            .Init()
            .setContext(application)
            .start()
    }

    /**
     * 不需要立即初始化的放在这里进行后台初始化
     */
    override fun initByBackstage() {
        initX5WebViewCore()
    }

    private fun initToast(): String {
        ToastUtils.init(BaseApplication.application)
        return "ToastUtils -->> init complete"
    }

    private fun initMojito(): String {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(initIntercept())
            .build()
        Mojito.initialize(
            GlideImageLoader.with(BaseApplication.context, okHttpClient),
            SketchImageLoadFactory()
        )
        return "Mojito -->> init complete"
    }

    private fun initIntercept(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)

            val responseHeaders = response.headers

            val responseHeadersLength: Int = responseHeaders.size
            for (i in 0 until responseHeadersLength) {
                val headerName = responseHeaders.name(i)
                val headerValue = responseHeaders[headerName]
                if(headerName == RetrofitFactory.L_C_I && !TextUtils.isEmpty(headerValue)){
                    Log.e("获取的headers=${RetrofitFactory.L_C_I}", headerValue!!)
                    SpUtils.putString(RetrofitFactory.L_C_I, headerValue)
                    break
                }
            }
            response
        }
    }
    /**
     * 初始化网络状态监听客户端
     * @return Unit
     */
    private fun initLoadSir(): String {
        LoadSir.beginBuilder()
            .addCallback(ErrorCallback())
            .addCallback(EmptyCallback())
            .addCallback(LoadingCallback())
            .setDefaultCallback(LoadingCallback::class.java)
            .commit()
        return "LoadSir -->> init complete"
    }
    /**
     * 初始化网络状态监听客户端
     * @return Unit
     */
    private fun initNetworkStateClient(): String {
        NetworkStateClient.register()
        return "NetworkStateClient -->> init complete"
    }

    /**
     * 腾讯TBS WebView X5 内核初始化
     */
    private fun initX5WebViewCore() {
        // dex2oat优化方案
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)

        // 允许使用非wifi网络进行下载
        QbSdk.setDownloadWithoutWifi(true)

        // 初始化
        QbSdk.initX5Environment(BaseApplication.context, object : QbSdk.PreInitCallback {

            override fun onCoreInitFinished() {
                Log.d("ApplicationInit", " TBS X5 init finished")
            }

            override fun onViewInitFinished(p0: Boolean) {
                // 初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核
                Log.d("ApplicationInit", " TBS X5 init is $p0")
            }
        })
    }

    /**
     * 腾讯 MMKV 初始化
     */
    private fun initMMKV(): String {
        val result = SpUtils.initMMKV(BaseApplication.context)
        return "MMKV -->> $result"
    }

    /**
     * 阿里路由 ARouter 初始化
     */
    private fun initARouter(): String {
        // 测试环境下打开ARouter的日志和调试模式 正式环境需要关闭
        if (BuildConfig.VERSION_TYPE != VersionStatus.RELEASE) {
            ARouter.openLog()     // 打印日志
            ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(BaseApplication.application)
        return "ARouter -->> init complete"
    }

    /**
     * 初始化 腾讯Bugly
     * 测试环境应该与正式环境的日志收集渠道分隔开
     * 目前有两个渠道 测试版本/正式版本
     */
    private fun initTencentBugly(): String {
        // 第三个参数为SDK调试模式开关
//        CrashReport.initCrashReport(
//            BaseApplication.context,
//            BaseApplication.context.getString(R.string.BUGLY_APP_ID),
//            BuildConfig.VERSION_TYPE != VersionStatus.RELEASE
//        )
        return "Bugly -->> init complete"
    }
}