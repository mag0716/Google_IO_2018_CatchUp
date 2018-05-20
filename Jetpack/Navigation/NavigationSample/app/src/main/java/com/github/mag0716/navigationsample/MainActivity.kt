package com.github.mag0716.navigationsample

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, NavController.OnNavigatedListener {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var container: NavHostFragment
//    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        container = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment

        // TODO: ActionBar と Navigation Graph の連動(Codelab)
        // これで共存させても動作するようになるが、stack に積まれると Navigation Drawer が Up キーとなる
        // BottomNavigationView のタブ切り替えでも stack に積まれるので希望の動作ではない
        //NavigationUI.setupActionBarWithNavController(this, container.navController, drawer_layout)

        // TODO: Up キーと Navigation Drawer を共存させると、Navigation Drawer が動作するみたいなので解決策が分かるまで無効化
//        toggle = ActionBarDrawerToggle(
//                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
//        drawer_layout.addDrawerListener(toggle)
//        toggle.syncState()
//        nav_view.setNavigationItemSelectedListener(this)

        // BottomNavigationView と Navigation Graph を連動させる
        // BottomNavigationView に渡す menu に指定する id は Navigation Graph の destination の id と同じにする必要がある
        NavigationUI.setupWithNavController(bottomNavigation, container.navController)

        Log.d(TAG, "onCreate : ${container.navController.currentDestination.label}")
    }

    override fun onResume() {
        super.onResume()
        container.navController.addOnNavigatedListener(this)
    }

    override fun onPause() {
        super.onPause()
        container.navController.removeOnNavigatedListener(this)
    }

    override fun onSupportNavigateUp(): Boolean {
        Log.d(TAG, "onSupportNavigationUp")
        return container.navController.navigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected : $item")
        when (item.itemId) {
//            android.R.id.home -> {
//                val fragment = supportFragmentManager.findFragmentById(R.id.container)
//                if (fragment is ParentFragment) {
//                    fragment.popAllFragment()
//                }
//                return true
//            }
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

//        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onNavigated(controller: NavController, destination: NavDestination) {
        Log.d(TAG, "onNavigated : ${destination.label}")
    }

    fun updateToolbar(title: String, hasUpKey: Boolean = false) {
        Log.d(TAG, "updateToolbar $title, $hasUpKey")
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(hasUpKey)
    }

    fun moveToChild(count: Int = 0) {
        val bundle = bundleOf(ChildFragment.KEY to count)
        container.navController.navigate(R.id.action_child, bundle)
    }

    private fun moveToSettings() {
        startActivity(Intent(this, SettingsActivity::class.java))
    }
}
