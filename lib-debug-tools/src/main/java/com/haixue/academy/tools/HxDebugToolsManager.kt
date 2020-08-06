package com.haixue.academy.tools

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.content.res.Resources
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.marginTop
import com.didichuxing.doraemonkit.DoraemonKit
import com.didichuxing.doraemonkit.DoraemonKit.showToolPanel
import com.didichuxing.doraemonkit.kit.AbstractKit
import com.haixue.academy.tools.dorkit.*
import com.haixue.academy.tools.dorkit.kit.HxActivityEntryKit
import com.haixue.academy.tools.swissarmyknife.SAKManager
import com.haixue.academy.tools.utils.SpUtil
import com.person.ermao.lib_debug_tools.R
import leakcanary.LeakCanary
import kotlin.math.abs


object HxDebugToolsManager {
    private val kits: LinkedHashMap<String, MutableList<AbstractKit>> = LinkedHashMap()
    private val hxKits: MutableList<AbstractKit> = ArrayList()

    @JvmStatic
    fun install(app: Application) {
        install(app, mutableListOf(), mutableListOf())
    }


    @JvmStatic
    fun install(app: Application, customKits: List<AbstractKit>) {
        install(app, customKits, mutableListOf())
    }

    /**
     * @app application
     * @customKits 展示在嗨学工具栏里面的自定义工具，其中有：EnvSwitchKit:跳转到切换环境的页面
     *                                               HxActivityEntryKit:打开一个Activity界面,
     *                                               HxFragmentEntryKit:打开一个Fragment界面,
     *                                               HxSimpleKit:自定义处理任意事情
     * @switchItemKits 开关配置界面可配置的自定义开关选项,参考TempSwitch
     * */
    @JvmStatic
    fun install(app: Application, customKits: List<AbstractKit>, switchItemKits: List<IItem>) {
        itemList.addAll(switchItemKits)
        hxKits.add(HxActivityEntryKit.Builder().iconName(R.string.app_info).iconRes(R.mipmap.ic_app_info).entryActivity(AppInfoActivity::class.java as Class<*>).build())
        hxKits.add(HxActivityEntryKit.Builder().iconName(R.string.switch_info).iconRes(R.mipmap.ic_switch).entryActivity(SwitchKitActivity::class.java as Class<*>).build())

        hxKits.addAll(customKits)
        kits["嗨学工具"] = hxKits
        DoraemonKit.install(app, kits, "")
        DoraemonKit.hide()
        initAllActivityCallBack(app)
        if (SpUtil.getInstance(app.applicationContext).getBoolean(KEY_BLOCK_CANARY)) {
            loadBlockCanary(app.applicationContext)
        }
        if(SpUtil.getInstance(app.applicationContext).getBoolean(KEY_SWISS_ARMY_KNIFE)){
            SAKManager.install(app)
        }
        loadLeakCanary(app.applicationContext)
    }

    private fun initAllActivityCallBack(app: Application) {
        //注册所有activity生命周期回调监听 实现在每一个activity摇一摇都能出现DiDiKit图标
        var sensorManager: SensorManager? = null
        app.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {

            override fun onActivityPaused(activity: Activity) {
                sensorManager?.unregisterListener(sensorEventListener)
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
            }

            override fun onActivityStopped(activity: Activity) {
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                sensorManager = app.applicationContext.getSystemService(SENSOR_SERVICE) as SensorManager?
            }

            override fun onActivityResumed(activity: Activity) {

                sensorManager?.registerListener(sensorEventListener, sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);

                if (SpUtil.getInstance(activity).getBoolean(KEY_CURRENT_ACTIVITY)) {
                    val content: ViewGroup = activity.findViewById(android.R.id.content)
                    val textView = TextView(activity)
                    textView.text = activity::class.java.simpleName
                    textView.textSize = 10.dp
                    textView.setBackgroundResource(R.color.text_bg_color)
                    textView.setPadding(20.dp.toInt(), 20.dp.toInt(), 0, 0)
                    val layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    content.addView(textView, layoutParams)
                }
            }

        })
    }

    private val sensorEventListener = object : SensorEventListener {
        override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        }

        override fun onSensorChanged(event: SensorEvent?) {
            // 传感器信息改变时执行该方法
            // 传感器信息改变时执行该方法
            val values = event!!.values
            val x = values[0] // x轴方向的重力加速度，向右为正

            val y = values[1] // y轴方向的重力加速度，向前为正

            val z = values[2] // z轴方向的重力加速度，向上为正

            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            val mediumValue = 19 // 三星 i9250怎么晃都不会超过20，没办法，只设置19了

            if (abs(x) > mediumValue || abs(y) > mediumValue || abs(z) > mediumValue) {
                //展示DiDiKit图标
                if (!DoraemonKit.isShow) {
                    showToolPanel()
                }
            }
        }

    }

    /**
     * 安装leackCanary
     *
     * @param app
     */
    private fun loadBlockCanary(context: Context) {
        //反射调用
        try {
            val blockCanaryManager = Class.forName("com.github.moduth.blockcanary.BlockCanary")
            val blockCanaryContext = Class.forName("com.github.moduth.blockcanary.BlockCanaryContext")
            val newInstance = blockCanaryContext.newInstance()
            val install = blockCanaryManager.getMethod("install", Context::class.java, newInstance::class.java)
            //调用静态的install方法
            install.invoke(null, context, newInstance)
        } catch (e: Exception) {
            Log.e("blockCanaryManager", e.toString())
        }
    }

    private fun loadLeakCanary(context: Context) {
        LeakCanary.config = LeakCanary.config.copy(dumpHeap = SpUtil.getInstance(context).getBoolean(KEY_LEAK_CANARY))
    }
}

val Float.dp
    get() = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, Resources.getSystem().displayMetrics)
val Int.dp
    get() = this.toFloat().dp