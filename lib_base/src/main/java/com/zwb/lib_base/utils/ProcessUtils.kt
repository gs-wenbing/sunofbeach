package com.zwb.lib_base.utils

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Process
import android.text.TextUtils


/**
 * 进程工具类
 *
 * @author Qu Yunshuo
 * @since 3/16/21 9:06 AM
 */
object ProcessUtils {

    /**
     * 获取当前所有进程
     *
     * @param context Context 上下文
     * @return List<ActivityManager.RunningAppProcessInfo> 当前所有进程
     */
    fun getRunningAppProcessList(context: Context): List<ActivityManager.RunningAppProcessInfo> {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        return activityManager.runningAppProcesses
    }

    /**
     * 判断该进程id是否属于该进程名的进程
     *
     * @param context Context 上下文
     * @param processId Int 进程Id
     * @param processName String 进程名
     * @return Boolean
     */
    fun isPidOfProcessName(context: Context, processId: Int, processName: String): Boolean {
        // 遍历所有进程找到该进程id对应的进程
        for (process in getRunningAppProcessList(context)) {
            if (process.pid == processId) {
                // 判断该进程id是否和进程名一致
                return (process.processName == processName)
            }
        }
        return false
    }

    /**
     * 获取主进程名
     *
     * @param context Context 上下文
     * @return String 主进程名
     * @throws PackageManager.NameNotFoundException if a package with the given name cannot be found on the system.
     */
    @Throws(PackageManager.NameNotFoundException::class)
    fun getMainProcessName(context: Context): String {
        val applicationInfo = context.packageManager.getApplicationInfo(context.packageName, 0)
        return applicationInfo.processName
    }

    /**
     * 判断当前进程是否是主进程
     *
     * @param context Context 上下文
     * @return Boolean
     * @throws PackageManager.NameNotFoundException if a package with the given name cannot be found on the system.
     */
    @Throws(PackageManager.NameNotFoundException::class)
    fun isMainProcess(context: Context): Boolean {
        val processId = Process.myPid()
        val mainProcessName = getMainProcessName(context)
        return isPidOfProcessName(context, processId, mainProcessName)
    }

    fun isAppExist(packageName: String, context: Context): Boolean {
        val pm = context.packageManager
        return try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    @SuppressLint("WrongConstant")
    fun isAppAlive(context: Context, packageName: String): Intent? {
        //Activity完整名
        val mainAct: String?
        val pkgMag = context.packageManager
        val intent = Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.flags = Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED or Intent.FLAG_ACTIVITY_NEW_TASK;

        val list = pkgMag.queryIntentActivities(intent, PackageManager.GET_ACTIVITIES)
        val item = list.find { it.activityInfo.packageName.equals(packageName) }
        mainAct = item?.activityInfo?.name
        if (TextUtils.isEmpty(mainAct)) {
            return null
        }
        intent.component = ComponentName(packageName, mainAct!!)
        return intent
    }

    fun getPackageContext(context: Context, packageName: String): Context? {
        var pkgContext: Context? = null
        if (context.getPackageName().equals(packageName)) {
            pkgContext = context;
        } else {
            // 创建第三方应用的上下文环境
            try {
                pkgContext = context.createPackageContext(
                    packageName,
                    Context.CONTEXT_IGNORE_SECURITY
                            or Context.CONTEXT_INCLUDE_CODE
                )
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace();
            }
        }
        return pkgContext
    }

    fun openPackage(context: Context, packageName: String): Boolean {
        val pkgContext = getPackageContext(context, packageName)
        val intent: Intent? = isAppAlive(context, packageName)
        if (pkgContext != null && intent != null) {
            pkgContext.startActivity(intent)
            return true
        }
        return false
    }
}