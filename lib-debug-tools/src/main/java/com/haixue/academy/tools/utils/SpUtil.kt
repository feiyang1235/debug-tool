package com.haixue.academy.tools.utils

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by XiaoChen on 2016/6/22 0022.
 * Project: 嗨学
 */
class SpUtil private constructor(var context: Context) {
    private val SP_NAME = "debug_sp"

    /**
     * SharedPreferences对象
     */
    private var sp: SharedPreferences
    /**
     * 读取字符串
     *
     * @param key 键
     * @return String类型的值
     */
    fun getString(key: String?): String? {
        return sp.getString(key, "")
    }

    /**
     * 保持字符串
     *
     * @param key   键
     * @param value 值
     */
    fun putString(key: String?, value: String?) {
        val edit = sp.edit()
        edit.putString(key, value)
        edit.apply()
    }

    /**
     * 读取布尔类型数据
     *
     * @param key 键
     * @return Boolean类型的值
     */
    fun getBoolean(key: String?): Boolean {
        return sp.getBoolean(key, false)
    }

    /**
     * 读取布尔类型数据
     *
     * @param key 键
     * @param defaultValue 默认值
     * @return Boolean类型的值
     */
    fun getBoolean(key: String?, defaultValue: Boolean): Boolean {
        return sp.getBoolean(key, defaultValue)
    }

    /**
     * 保存布尔类型的数据
     *
     * @param key   键
     * @param value 值
     */
    fun putBoolean(key: String?, value: Boolean?) {
        val edit = sp.edit()
        edit.putBoolean(key, value!!)
        edit.apply()
    }

    /**
     * 读取整型类型数据
     *
     * @param key 键
     * @return Int类型的值
     */
    fun getInt(key: String?): Int {
        return sp.getInt(key, -1)
    }

    /**
     * 保存整型类型的数据
     *
     * @param key   键
     * @param value 值
     */
    fun putInt(key: String?, value: Int?) {
        val edit = sp.edit()
        edit.putInt(key, value!!)
        edit.apply()
    }
    companion object {
        /**
         * 本类对象
         */
        @Volatile
        private  var instance: SpUtil?=null

        /**
         * 对外提供获取本类对象的方法
         *
         * @param context 上下文
         * @return SharedPreferences对象
         */
        @Synchronized
        fun getInstance(context: Context): SpUtil {
            if (instance == null) {
                synchronized(SpUtil::class.java) {
                    if (instance == null) {
                        instance = SpUtil(context.applicationContext)
                    }
                }
            }
            return instance!!
        }

    }

    /**
     * 私有构造（单例）
     *
     * @param context 上下文
     */
    init {
        //更新,用包名生成sp,避免sp的冲突
        sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE /*| Context.MODE_MULTI_PROCESS*/) //过时flag，设置时，读取sp会从内存读
    }
}