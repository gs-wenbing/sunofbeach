package com.zwb.sob_login

import android.content.Context
import android.text.TextUtils
import android.util.Log
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.module.AppGlideModule
import com.zwb.lib_base.net.RetrofitFactory.Companion.L_C_I
import com.zwb.lib_base.utils.EventBusUtils
import com.zwb.lib_base.utils.LogUtils
import com.zwb.lib_base.utils.SpUtils
import com.zwb.sob_login.event.LoginEvent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.io.InputStream
import java.io.UnsupportedEncodingException
import java.net.URLDecoder

/**
 *
 */
@GlideModule
class OkHttpGlideModule : AppGlideModule() {
    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(initIntercept())
            .build()
        registry.replace(
            GlideUrl::class.java,
            InputStream::class.java,
            OkHttpUrlLoader.Factory(okHttpClient)
        )
    }

    override fun isManifestParsingEnabled(): Boolean {
        return false
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
                if(headerName == L_C_I && !TextUtils.isEmpty(headerValue)){
                    Log.e("获取的headers=$L_C_I", headerValue!!)
                    SpUtils.putString(L_C_I, headerValue)
                    break
                }
            }
            response
        }
    }
}