package com.haixue.academy.tools.dorkit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.haixue.academy.tools.swissarmyknife.SAKManager
import com.haixue.academy.tools.utils.SpUtil
import com.person.ermao.lib_debug_tools.R
import com.wanjian.sak.SAK
import com.wanjian.sak.config.Config
import kotlinx.android.synthetic.main.activity_switch_kit.*
import kotlin.system.exitProcess

const val KEY_LEAK_CANARY = "KEY_LEAK_CANARY"
const val KEY_BLOCK_CANARY = "KEY_BLOCK_CANARY"
const val KEY_SWISS_ARMY_KNIFE = "KEY_SWISS_ARMY_KNIFE"
const val KEY_CURRENT_ACTIVITY = "KEY_CURRENT_ACTIVITY"
val itemList: MutableList<IItem> = ArrayList()

class SwitchKitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_switch_kit)
        tv_reload.setOnClickListener {
            val i: Intent = baseContext.packageManager.getLaunchIntentForPackage(baseContext.packageName)!!
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(i)
            exitProcess(0)
        }
        loadView("leakCanary性能检测(重启后生效)", KEY_LEAK_CANARY)
        loadView("blockcanary性能检测(重启后生效)", KEY_BLOCK_CANARY)
        loadView("SwissArmyKnife工具开关", KEY_SWISS_ARMY_KNIFE, {
            SAKManager.install(application)
        }, {
            SAKManager.unInstall()
        })
        itemList.forEach {
            loadView(it.title, it.key, it.positive, it.negative)
        }
        loadView("前台Activity展示(重启后生效)", KEY_CURRENT_ACTIVITY)
    }

    private fun loadView(title: String, key: String, positive: (() -> Unit)? = null, negative: (() -> Unit)? = null) {
        val inflateView = LayoutInflater.from(this).inflate(R.layout.layout_check_kit, null);
        val tvName = inflateView.findViewById<TextView>(R.id.tv_name)
        val checkBox = inflateView.findViewById<CheckBox>(R.id.check_box)
        checkBox.isChecked = SpUtil.getInstance(this).getBoolean(key)
        tvName.text = title
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            SpUtil.getInstance(this).putBoolean(key, isChecked)
            if (isChecked) {
                positive?.let { it() }
            } else {
                negative?.let { it() }
            }
        }
        inflateView.tag = key
        ll_wrapper.addView(inflateView)
    }
}

interface IItem {
    /**
     * 名称
     *
     */
    val title: String

    /**
     * 存储key
     *
     */
    val key: String

    /**
     * 选中需要做的事
     *
     */
    val positive: () -> Unit

    /**
     * 未选中需要做的事
     *
     */
    val negative: () -> Unit
}

/**
 * 示例
 * */
class TempSwitch : IItem {
    override val title: String
        get() = "title name"
    override val key: String
        get() = "Unique key"
    override val positive: () -> Unit
        get() = ::doSomething
    override val negative: () -> Unit
        get() = ::doSomething

    private fun doSomething() {

    }
}
