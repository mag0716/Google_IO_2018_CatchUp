package com.github.mag0716.navigationsample

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var homeFragment: ParentFragment? = null
    var dashboardFragment: ParentFragment? = null
    var notificationsFragment: ParentFragment? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchTab(item.itemId)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                switchTab(item.itemId)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                switchTab(item.itemId)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            switchTab(R.id.navigation_home)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun switchTab(id: Int) {
        var title: String? = null
        var fragment: ParentFragment? = null
        when (id) {
            R.id.navigation_home -> {
                title = "home"
                if (homeFragment == null) {
                    homeFragment = ParentFragment.newInstance(title)
                }
                fragment = homeFragment
            }
            R.id.navigation_dashboard -> {
                title = "dashboard"
                if (dashboardFragment == null) {
                    dashboardFragment = ParentFragment.newInstance(title)
                }
                fragment = dashboardFragment
            }
            R.id.navigation_notifications -> {
                title = "notifications"
                if (notificationsFragment == null) {
                    notificationsFragment = ParentFragment.newInstance(title)
                }
                fragment = notificationsFragment
            }
        }

        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()
        }

        if (title != null) {
            updateToolbar(title)
        }
    }

    fun updateToolbar(title: String, hasUpKey: Boolean = false) {
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(hasUpKey)
    }
}
