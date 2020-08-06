package com.haixue.academy.tools.dorkit.kit

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.didichuxing.doraemonkit.kit.AbstractKit
import com.haixue.academy.tools.IHxKit

open class HxActivityEntryKit(@DrawableRes private var iconRes: Int, @StringRes private var iconName: Int, private var entryActivity: Class<*>?) : AbstractKit(),IHxKit {
    override val icon: Int
        get() = iconRes
    override val name: Int
        get() = iconName
    private  val TAG = "HxActivityEntryKit"
    override fun onAppInit(context: Context?) {

    }

    override fun onClick(context: Context?) {
        try {
            if (entryActivity != null) {
                context?.startActivity(Intent(context, entryActivity))
            }
        }catch (e:Exception){
            Log.e(TAG, "onClick: $e" )
        }
    }

    class Builder {
        private var iconRes: Int = 0
        private var iconName: Int = 0
        private var entryActivity: Class<*>? = null
        fun iconRes(@DrawableRes iconRes: Int):Builder {
                this.iconRes = iconRes
            return this
        }
        fun iconName(@StringRes iconName: Int):Builder {
            this.iconName = iconName
            return this
        }
        fun entryActivity(entryActivity: Class<*>):Builder {
            this.entryActivity = entryActivity
            return this
        }
        fun build():HxActivityEntryKit{
            return HxActivityEntryKit(iconRes,iconName,entryActivity)
        }
    }
}