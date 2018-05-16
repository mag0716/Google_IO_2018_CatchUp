package com.github.mag0716.navigationsample

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName

        const val HOME = "home"
        const val DASHBOARD = "dashboard"
        const val NOTIFICATIONS = "notifications"
    }

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
        } else {
            homeFragment = supportFragmentManager.findFragmentByTag(HOME) as? ParentFragment
            dashboardFragment = supportFragmentManager.findFragmentByTag(DASHBOARD) as? ParentFragment
            notificationsFragment = supportFragmentManager.findFragmentByTag(NOTIFICATIONS) as? ParentFragment
        }
        Log.d(TAG, "onCreate : $homeFragment, $dashboardFragment, $notificationsFragment")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                val fragment = supportFragmentManager.findFragmentById(R.id.container)
                if (fragment is ParentFragment) {
                    fragment.popAllFragment()
                }
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    fun switchTab(id: Int) {
        Log.d(TAG, "switchTab : $id")
        var title: String? = null
        var fragment: ParentFragment? = null
        var newFragment = true
        when (id) {
            R.id.navigation_home -> {
                title = HOME
                if (homeFragment == null) {
                    homeFragment = ParentFragment.newInstance(title)
                } else {
                    newFragment = false
                }
                fragment = homeFragment
            }
            R.id.navigation_dashboard -> {
                title = DASHBOARD
                if (dashboardFragment == null) {
                    dashboardFragment = ParentFragment.newInstance(title)
                } else {
                    newFragment = false
                }
                fragment = dashboardFragment
            }
            R.id.navigation_notifications -> {
                title = NOTIFICATIONS
                if (notificationsFragment == null) {
                    notificationsFragment = ParentFragment.newInstance(title)
                } else {
                    newFragment = false
                }
                fragment = notificationsFragment
            }
        }

        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.setPrimaryNavigationFragment(fragment)
            val currentFragment = supportFragmentManager.findFragmentById(R.id.container)
            if (currentFragment != null) {
                transaction.detach(currentFragment)
            }
            if (newFragment) {
                transaction.add(R.id.container, fragment, title)
            } else {
                transaction.attach(fragment)
            }
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
