package com.haixue.academy.tools.dorkit.kit

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.person.ermao.lib_debug_tools.R

class EnvSwitchKit(enterActivity: Class<*>?) : HxActivityEntryKit(R.mipmap.ic_switch_env, R.string.switch_env, enterActivity) {

    class Builder {
        private var entryActivity: Class<*>? = null
        fun entryActivity(entryActivity: Class<*>): Builder {
            this.entryActivity = entryActivity
            return this
        }

        fun build(): EnvSwitchKit {
            return EnvSwitchKit(entryActivity)

        }
    }
}