package com.zwb.lib_base.net
import android.text.TextUtils
import android.util.Log
import com.zwb.lib_base.utils.LogUtils
import com.zwb.lib_base.utils.SpUtils
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.UnsupportedEncodingException
import java.net.URLDecoder


/**
 * Created with Android Studio.
 * Description:
 * @date: 2020/02/24
 * Time: 16:56
 */

class RetrofitFactory private constructor() {

    companion object {
        const val SOB_TOKEN = "sob_token"
        const val L_C_I = "l_c_i"

        val instance by lazy {
            RetrofitFactory()
        }
    }

    fun <Service> getService(serviceClass: Class<Service>, baseUrl: String): Service {
        return Retrofit.Builder()
            .client(initOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(serviceClass)
    }
    private fun initOkHttpClient(): OkHttpClient {

        return OkHttpClient.Builder()
            .addInterceptor(initLoggingIntercept())
            .addInterceptor(initCookieIntercept())
            .addInterceptor(initLoginIntercept())
            .addInterceptor(initCommonInterceptor())
            .build()
    }
    private fun initLoggingIntercept(): Interceptor {
        return HttpLoggingInterceptor { message ->
            try {
                val text: String = replacer(message)
                LogUtils.e("OKHttp-----", text)
            } catch (e: UnsupportedEncodingException) {
                e.printStackTrace()
                LogUtils.e("OKHttp-----", message)
            }
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
    private fun replacer(outBuffer: String): String {
        var data = outBuffer
        try {
            data = data.replace("%(?![0-9a-fA-F]{2})".toRegex(), "%25")
            data = data.replace("\\+".toRegex(), "%2B")
            data = URLDecoder.decode(data, "utf-8")
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return data
    }
    private fun initCookieIntercept(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val response = chain.proceed(request)
            response
        }
    }

    private fun initLoginIntercept(): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val builder = request.newBuilder()
            val response = chain.proceed(builder.build())
            response
        }
    }

    private fun initCommonInterceptor(): Interceptor {
        return Interceptor { chain ->
            val builder = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("charset", "UTF-8")
            val lci = SpUtils.getString(L_C_I,"")
            if(!TextUtils.isEmpty(lci)){
                builder.addHeader(L_C_I, lci!!)
                Log.e("设置的headers=$L_C_I", lci)
            }
            val sobToken = SpUtils.getString(SOB_TOKEN,"")
            if(!TextUtils.isEmpty(sobToken)){
                builder.addHeader(SOB_TOKEN, sobToken!!)
            }
            val response = chain.proceed(builder.build())

            val responseHeaders = response.headers
            val responseHeadersLength: Int = responseHeaders.size
            for (i in 0 until responseHeadersLength) {
                val headerName = responseHeaders.name(i)
                val headerValue = responseHeaders[headerName]
                if(headerName == SOB_TOKEN && !TextUtils.isEmpty(headerValue)){
                    SpUtils.putString(SOB_TOKEN, headerValue!!)
                    break
                }
            }
            response
        }
    }



}