package com.haixue.academy.tools.utils

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageInfo
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import java.text.SimpleDateFormat

//跟App相关的辅助类

//跟App相关的辅助类
object AppUtils {
    /**
     *
     * 获取应用程序名称
     *
     */
    @Synchronized
    fun getAppName(context: Context): String {
        try {
            val packageManager: PackageManager = context.packageManager
            val packageInfo: PackageInfo = packageManager.getPackageInfo(
                    context.packageName, 0)
            val labelRes: Int = packageInfo.applicationInfo.labelRes
            return context.resources.getString(labelRes)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "unknown"
    }

    /**
     *
     *
     * @param context
     *
     * @return 当前应用的版本名称
     */
    @Synchronized
    fun getVersionName(context: Context): String {
        try {
            val packageManager: PackageManager = context.packageManager
            val packageInfo: PackageInfo = packageManager.getPackageInfo(
                    context.packageName, 0)
            return packageInfo.versionName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "unknown"
    }

    /**
     *
     *
     * @param context
     *
     * @return 当前应用的版本号
     */
    @Synchronized
    fun getVersionCode(context: Context): Long {
        try {
            val packageManager: PackageManager = context.packageManager
            val packageInfo: PackageInfo = packageManager.getPackageInfo(
                    context.packageName, 0)
            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                packageInfo.longVersionCode
            } else {
                packageInfo.versionCode.toLong()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }

    /**
     *
     *
     * @param context
     *
     * @return 当前应用的包名（applicationId）
     */
    @Synchronized
    fun getPackageName(context: Context): String {
        try {
            val packageManager: PackageManager = context.packageManager
            val packageInfo: PackageInfo = packageManager.getPackageInfo(
                    context.packageName, 0)
            return packageInfo.packageName
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return "unknown"
    }
    /**
     *
     * 获取图标 bitmap
     *
     * @param context
     */
    @Synchronized
    fun getDrawable(context: Context): Drawable? {
        var packageManager: PackageManager? = null
        var applicationInfo: ApplicationInfo?
        try {
            packageManager = context.applicationContext
                    .packageManager
            applicationInfo = packageManager.getApplicationInfo(
                    context.packageName, 0)
            return applicationInfo.loadIcon(packageManager)
        } catch (e: PackageManager.NameNotFoundException) {
            applicationInfo = null
        }
        return null
    }
    /**
     *
     * 获取图标 bitmap
     *
     * @param context
     */
    @Synchronized
    fun getBitmap(context: Context): Bitmap? {
        var packageManager: PackageManager? = null
        var applicationInfo: ApplicationInfo?
        try {
            packageManager = context.applicationContext
                    .packageManager
            applicationInfo = packageManager.getApplicationInfo(
                    context.packageName, 0)
            val d: Drawable? =  applicationInfo.loadIcon(packageManager) //xxx根据自己的情况获取drawable
            val bd: BitmapDrawable = d as BitmapDrawable
            return bd.bitmap
        } catch (e: PackageManager.NameNotFoundException) {
            applicationInfo = null
        }
        return null
    }
    /**
     *
     * 获取Uid
     *
     * @param context
     */
    @Synchronized
    fun getUid(context: Context): Int {
        var packageManager: PackageManager? = null
        var applicationInfo: ApplicationInfo?
        try {
            packageManager = context.applicationContext
                    .packageManager
            applicationInfo = packageManager.getApplicationInfo(
                    context.packageName, 0)

            return applicationInfo.uid
        } catch (e: PackageManager.NameNotFoundException) {

        }
        return 0
    }
    /**
     *
     * 获取apk存放路径
     *
     * @param context
     */
    @Synchronized
    fun getApkPath(context: Context): String {
        var packageManager: PackageManager?
        var applicationInfo: ApplicationInfo?
        try {
            packageManager = context.applicationContext
                    .packageManager
            applicationInfo = packageManager.getApplicationInfo(
                    context.packageName, 0)

            return applicationInfo.sourceDir
        } catch (e: PackageManager.NameNotFoundException) {

        }
        return "unknown"
    }
    /**
     *
     * 获取私有数据存放路径
     *
     * @param context
     */
    @Synchronized
    fun getDBPath(context: Context): String {
        var packageManager: PackageManager?
        var applicationInfo: ApplicationInfo?
        try {
            packageManager = context.applicationContext
                    .packageManager
            applicationInfo = packageManager.getApplicationInfo(
                    context.packageName, 0)

            return applicationInfo.dataDir
        } catch (e: PackageManager.NameNotFoundException) {

        }
        return "unknown"
    }
    /**
     *
     * 获取native JNI库存放路径
     *
     * @param context
     */
    @Synchronized
    fun getJNILibraryPath(context: Context): String {
        var packageManager: PackageManager?
        var applicationInfo: ApplicationInfo?
        try {
            packageManager = context.applicationContext
                    .packageManager
            applicationInfo = packageManager.getApplicationInfo(
                    context.packageName, 0)

            return applicationInfo.nativeLibraryDir
        } catch (e: PackageManager.NameNotFoundException) {

        }
        return "unknown"
    }
    /***
     *是否可debug
     *
     */
     fun isDebuggable(context: Context):Boolean{
        var packageManager: PackageManager?
        var applicationInfo: ApplicationInfo?
        try {
            packageManager = context.applicationContext
                    .packageManager
            applicationInfo = packageManager.getApplicationInfo(
                    context.packageName, 0)

            return (applicationInfo.flags and  ApplicationInfo.FLAG_DEBUGGABLE) == ApplicationInfo.FLAG_DEBUGGABLE
        } catch (e: PackageManager.NameNotFoundException) {

        }
        return true;
    }
    /***
     *minSdkVersion
     *
     */
    fun getMinSdkVersion(context: Context):Int{
        var packageManager: PackageManager?
        var applicationInfo: ApplicationInfo?
        try {
            packageManager = context.applicationContext
                    .packageManager
            applicationInfo = packageManager.getApplicationInfo(
                    context.packageName, 0)

            return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                applicationInfo.minSdkVersion
            } else {
                0
            }
        } catch (e: PackageManager.NameNotFoundException) {

        }
        return 0;
    }
    /***
     *targetSdkVersion
     *
     */
    fun getTargetSdkVersion(context: Context):Int{
        var packageManager: PackageManager?
        var applicationInfo: ApplicationInfo?
        try {
            packageManager = context.applicationContext
                    .packageManager
            applicationInfo = packageManager.getApplicationInfo(
                    context.packageName, 0)

            return applicationInfo.targetSdkVersion
        } catch (e: PackageManager.NameNotFoundException) {

        }
        return 0;
    }
    /***
     *安装时间
     *
     */
    fun getInstallTime(context: Context):String{
        var packageManager: PackageManager?
        var applicationInfo: ApplicationInfo?
        try {
            packageManager = context.applicationContext
                    .packageManager
            applicationInfo = packageManager.getApplicationInfo(
                    context.packageName, 0)

            return getFormatDate(packageManager.getPackageInfo(context.packageName, 0).firstInstallTime)
        } catch (e: PackageManager.NameNotFoundException) {

        }
        return "unknown";
    }
    /***
     *更新时间
     *
     */
    fun getUpdateTime(context: Context):String{
        var packageManager: PackageManager?
        var applicationInfo: ApplicationInfo?
        try {
            packageManager = context.applicationContext
                    .packageManager
            applicationInfo = packageManager.getApplicationInfo(
                    context.packageName, 0)

            return getFormatDate(packageManager.getPackageInfo(context.packageName, 0).lastUpdateTime)
        } catch (e: PackageManager.NameNotFoundException) {

        }
        return "unknown";
    }
    private fun getFormatDate(time: Long): String {
        val simpleDateFormat = SimpleDateFormat("yyyy年MM月dd日 HH:mm:SS")
        return simpleDateFormat.format(time)
    }
    fun getAppInfo(context: Context):HashMap<String,String>{
        val map = HashMap<String,String>()

        map["App名称"] = getAppName(context)
//        map["VersionName"] = getVersionName(context)
//        map["VersionCode"] = getVersionCode(context).toString()
//        map["包名"] = getPackageName(context)
        map["Apk路径"] = getApkPath(context)
        map["数据库路径"] = getDBPath(context)
        map["Native库路径"] = getJNILibraryPath(context)
        map["isDebuggable"] = isDebuggable(context).toString()
//        map["minSdkVersion"] = getMinSdkVersion(context).toString()
//        map["targetSdkVersion"] = getTargetSdkVersion(context).toString()
        map["安装时间"] = getInstallTime(context)
        map["更新时间"] = getUpdateTime(context)
        return map
    }
}
