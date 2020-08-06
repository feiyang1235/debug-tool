package com.haixue.academy.tools.dorkit.kit

import android.content.Context
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.didichuxing.doraemonkit.kit.AbstractKit
import com.haixue.academy.tools.IHxKit
@Deprecated(message = "Retention method")
class HxSwitchKit(@DrawableRes private var iconRes: Int, @StringRes private var iconName: Int, private var positive: () -> Unit,private var negative: () -> Unit) : AbstractKit(), IHxKit {
    override val icon: Int
        get() = iconRes
    override val name: Int
        get() = iconName

    override fun onAppInit(context: Context?) {
    }

    override fun onClick(context: Context?) {

    }

    class Builder {
        private var iconRes: Int = 0
        private var iconName: Int = 0
        private lateinit var positive: () -> Unit
        private lateinit var negative: () -> Unit
        fun iconRes(@DrawableRes iconRes: Int): Builder {
            this.iconRes = iconRes
            return this
        }

        fun iconName(@StringRes iconName: Int): Builder {
            this.iconName = iconName
            return this
        }

        fun positive(positive: () -> Unit): Builder {
            this.positive = positive
            return this
        }
        fun negative(negative: () -> Unit): Builder {
            this.negative = negative
            return this
        }
        fun build(): HxSwitchKit {
            return HxSwitchKit(iconRes, iconName, positive,negative)
        }
    }
}