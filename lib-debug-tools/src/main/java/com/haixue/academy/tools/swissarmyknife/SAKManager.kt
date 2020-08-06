package com.haixue.academy.tools.swissarmyknife

import android.app.Application
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.wanjian.sak.SAK
import com.wanjian.sak.config.Config
import com.wanjian.sak.converter.ISizeConverter
import com.wanjian.sak.converter.Size
import com.wanjian.sak.filter.ViewFilter
import com.wanjian.sak.layer.AbsLayer
import com.wanjian.sak.layer.TakeColorView


object SAKManager {
    fun install(app:Application){
        SAK.init(app,null)
    }
    fun unInstall(){
        SAK.unInstall()
    }
    private fun getSizeConverter(): ISizeConverter? {
        return object : ISizeConverter() {
            // 可以添加自定义的SizeConverter，默认提供了Origin*、Px2Dp*，Px2Sp*
            override fun desc(): String {
                return "my converter"
            }

            override fun convert(context: Context?, length: Float): Size? {
                return Size.obtain().setLength(length / 2).setUnit("myU")
            }

            override fun recovery(context: Context?, length: Float): Int {
                return 0
            }
        }
    }

    private fun getLayerView(context: Context): AbsLayer? {
        // 可以添加自定义的view，自定义的view要继承自AbsLayer或其子类，AbsLayer是FrameLayout的子类
        // 当激活SwissArmyKnife时会调用 onAttached(View rootView)方法，rootView是当前window的根view，比如Activity的根view DecorView。
        //当停用是会调用`onDetached`
        return object : AbsLayer(context) {
            override fun description(): String? {
                return null
            }

            override fun icon(): Drawable? {
                return null
            }
        }
    }

    private fun getViewFilter(): ViewFilter? {
        return object : ViewFilter() {
            override fun apply(view: View?): Boolean {
                // 这里可以决定要显示哪种view，比如只显示ImageView子类和LinearLayout子类
                // 若想要显示所有可见的view，直接返回 view.getVisibility()==View.VISIBLE 即可
                return view is ImageView || view is LinearLayout
            }
        }
    }
}