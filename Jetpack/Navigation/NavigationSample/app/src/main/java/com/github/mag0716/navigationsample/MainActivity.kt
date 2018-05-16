package com.github.mag0716.navigationsample

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

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
        setSupportActionBar(toolbar)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            switchTab(R.id.navigation_home)
        } else {
            homeFragment = supportFragmentManager.findFragmentByTag(HOME) as? ParentFragment
            dashboardFragment = supportFragmentManager.findFragmentByTag(DASHBOARD) as? ParentFragment
            notificationsFragment = supportFragmentManager.findFragmentByTag(NOTIFICATIONS) as? ParentFragment
        }
        Log.d(TAG, "onCreate : $homeFragment, $dashboardFragment, $notificationsFragment")

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
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
            R.id.settings -> {
                moveToSettings()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_settings -> moveToSettings()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
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

    private fun moveToSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
}
