package com.haixue.academy.tools.dorkit

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.haixue.academy.tools.utils.AppUtils
import com.person.ermao.lib_debug_tools.R
import kotlinx.android.synthetic.main.activity_app_info.*

class AppInfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app_info)
        val listData: MutableList<Bean> = ArrayList()
        val appInfo = AppUtils.getAppInfo(this);
        for (mutableEntry in appInfo) {
            listData.add(Bean(mutableEntry.key, mutableEntry.value))
        }
        listView.adapter=InfoAdapter(this,listData)
        iv_logo.setImageDrawable(AppUtils.getDrawable(this))
    }
}

private class InfoAdapter(var context: Context, var dataList: List<Bean>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val viewHolder:ViewHolder
        val view:View
        if (convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_info, null)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        } else {
            viewHolder = convertView.tag as ViewHolder
            view = convertView
        }
            viewHolder.tvKey.text = getItem(position).key
            viewHolder.tvValue.text = getItem(position).value
        return view
    }


    override fun getItem(position: Int): Bean = dataList[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = dataList.size
    private class ViewHolder(var view:View) {
        var tvKey: TextView = view.findViewById(R.id.tv_key)
        var tvValue: TextView = view.findViewById(R.id.tv_value)
    }
}

data class Bean(var key: String?, var value: String?)