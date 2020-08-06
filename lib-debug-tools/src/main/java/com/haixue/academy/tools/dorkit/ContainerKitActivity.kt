package com.haixue.academy.tools.dorkit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.person.ermao.lib_debug_tools.R
import java.lang.Exception

@Suppress("UNCHECKED_CAST")
class ContainerKitActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment_wrapper)
        try {
            val fragment: Class<Fragment> = intent?.extras?.get("fragment") as Class<Fragment>
            addFragment(R.id.container, fragment.newInstance())
        } catch (e: Exception) {

        }
    }

    private fun addFragment(id: Int, fragment: Fragment) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        ft.replace(id, fragment)
        ft.commitAllowingStateLoss()
    }
}