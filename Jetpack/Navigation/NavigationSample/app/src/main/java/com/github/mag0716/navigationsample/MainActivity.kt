package com.github.mag0716.navigationsample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*

class MainActivity : AppCompatActivity(), NavController.OnNavigatedListener {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    private lateinit var container: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        container = supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment

        // ActionBar と Navigation Graph の連動(Codelabを参考にした)
        // FIXME:これで共存させても動作するようになるが、stack に積まれると Navigation Drawer が Up キーとなる。BottomNavigationView のタブ切り替えでも stack に積まれるので希望の動作ではない
        NavigationUI.setupActionBarWithNavController(this, container.navController, drawer_layout)

        // NavigationView と Navigation Graph の連動
        NavigationUI.setupWithNavController(navigation_view, container.navController)

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
        // NavigationView と Navigation Graph の連動に必要(Codelabを参考にした)
        return NavigationUI.navigateUp(drawer_layout,
                Navigation.findNavController(this, R.id.container))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d(TAG, "onOptionsItemSelected : $item")
        // Menu と Navigation Graph を連動させる(Codelabを参考にした)
        return NavigationUI.onNavDestinationSelected(item,
                Navigation.findNavController(this, R.id.container))
                || super.onOptionsItemSelected(item)
    }

    override fun onNavigated(controller: NavController, destination: NavDestination) {
        Log.d(TAG, "onNavigated : ${destination.label}")
    }

    fun updateToolbar(title: String, hasUpKey: Boolean = false) {
        Log.d(TAG, "updateToolbar $title, $hasUpKey")
        supportActionBar?.title = title
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    fun moveToChild(count: Int = 0) {
        val bundle = bundleOf(ChildFragment.KEY to count)
        container.navController.navigate(R.id.action_child, bundle)
    }
}
