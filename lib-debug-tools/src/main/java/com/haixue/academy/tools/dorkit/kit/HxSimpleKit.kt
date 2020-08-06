package com.haixue.academy.tools.dorkit.kit

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.didichuxing.doraemonkit.kit.AbstractKit
import com.haixue.academy.tools.IHxKit

/***
 *
 * 处理简单的操作
 * */
class HxSimpleKit(@DrawableRes private var iconRes: Int, @StringRes private var iconName: Int, private var doSomething: () -> Unit) : AbstractKit(), IHxKit {
    override val icon: Int
        get() = iconRes
    override val name: Int
        get() = iconName

    override fun onAppInit(context: Context?) {
    }

    override fun onClick(context: Context?) {
        doSomething()
    }

    class Builder {
        private var iconRes: Int = 0
        private var iconName: Int = 0
        private lateinit var doSomething: () -> Unit
        fun iconRes(@DrawableRes iconRes: Int): Builder {
            this.iconRes = iconRes
            return this
        }

        fun iconName(@StringRes iconName: Int): Builder {
            this.iconName = iconName
            return this
        }

        fun doSomething(doSomething: () -> Unit): Builder {
            this.doSomething = doSomething
            return this
        }

        fun build(): HxSimpleKit {
            return HxSimpleKit(iconRes, iconName, doSomething)
        }
    }
}