package com.haixue.academy.tools.dorkit.kit

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import com.didichuxing.doraemonkit.kit.AbstractKit
import com.haixue.academy.tools.IHxKit
import com.haixue.academy.tools.dorkit.ContainerKitActivity
import java.lang.Exception

class HxFragmentEntryKit(@DrawableRes private var iconRes: Int, @StringRes private var iconName: Int, private var entryFragment: Class<*>?) : AbstractKit(),IHxKit {
    override val icon: Int
        get() = iconRes
    override val name: Int
        get() = iconName

    override fun onAppInit(context: Context?) {

    }

    override fun onClick(context: Context?) {
        try {
            if (entryFragment != null) {
                context?.startActivity(Intent(context, ContainerKitActivity::class.java).putExtra("fragment",entryFragment))
            }
        }catch (e:Exception){

        }

    }

    class Builder {
        private var iconRes: Int = 0
        private var iconName: Int = 0
        private var entryFragment: Class<*>? = null
        fun iconRes(@DrawableRes iconRes: Int):Builder {
            this.iconRes = iconRes
            return this
        }
        fun iconName(@StringRes iconName: Int):Builder {
            this.iconName = iconName
            return this
        }
        fun entryFragment(entryFragment: Class<*>):Builder {
            this.entryFragment = entryFragment
            return this
        }
        fun build():HxFragmentEntryKit{
            return HxFragmentEntryKit(iconRes,iconName,entryFragment)
        }
    }
}