package com.github.mag0716.navigationsample

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var homeFragment: LabelFragment? = null
    var dashboardFragment: LabelFragment? = null
    var notificationsFragment: LabelFragment? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                replaceFragment(item.itemId)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                replaceFragment(item.itemId)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                replaceFragment(item.itemId)
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
            replaceFragment(R.id.navigation_home)
        }
    }

    fun replaceFragment(id: Int) {
        var fragment: LabelFragment? = null
        when (id) {
            R.id.navigation_home -> {
                if (homeFragment == null) {
                    homeFragment = LabelFragment.newInstance("home")
                }
                fragment = homeFragment
            }
            R.id.navigation_dashboard -> {
                if (dashboardFragment == null) {
                    dashboardFragment = LabelFragment.newInstance("dashboard")
                }
                fragment = dashboardFragment
            }
            R.id.navigation_notifications -> {
                if (notificationsFragment == null) {
                    notificationsFragment = LabelFragment.newInstance("notifications")
                }
                fragment = notificationsFragment
            }
        }

        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.container, fragment)
            transaction.commit()
        }
    }
}
