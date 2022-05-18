package com.zwb.lib_base.utils

import android.annotation.SuppressLint
import android.text.TextUtils
import android.text.format.DateUtils
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    /**
     * @param duration 秒钟
     */
    fun formatSeconds(duration: Int): String {
        var second = ""
        var minute = ""
        var time = ""
        //获取到时间
        val mm = duration / 60 //分
        val ss = duration % 60 //秒
        second = if (ss < 10) {
            "0$ss"
        } else {
            ss.toString()
        }
        minute = if (mm < 10) {
            "0$mm"
        } else {
            mm.toString() //分钟
        }
        time = "$minute:$second"
        return time
    }

    /**
     * 时间转换
     */
    fun timeFormat(dateTime: String?): String {
        if(TextUtils.isEmpty(dateTime))return ""
        var timestamp: Long = 0
        val currTimeStamp = Date().time
        try {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
            val date = simpleDateFormat.parse(dateTime!!)
            timestamp = date?.time!!
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        //将毫秒值转化成秒值
        val seconds = ((currTimeStamp - timestamp) / 1000)

        val str = StringBuffer()
        return when {
            seconds in 0..59 -> {
                str.append("刚刚").toString()
            }
            seconds in 60..3599 -> {
                str.append("${seconds / 60}分钟前").toString()
            }
            seconds >= 3600 && seconds < 3600 * 24 -> {
                str.append("${seconds / 3600}小时前").toString()
            }
            seconds in (3600 * 1 * 24) until 3600 * 2 * 24 -> {
                str.append("1天前").toString()
            }
            seconds in (3600 * 2 * 24) until 3600 * 3 * 24 -> {
                str.append("2天前").toString()
            }
            seconds in (3600 * 3 * 24) until 3600 * 4 * 24 -> {
                str.append("3天前").toString()
            }
            else -> {
                dateTime!!.substring(0,10)
            }
        }
    }
}