package com.github.mag0716.nestednavigation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController

class MainActivity : AppCompatActivity(), NavController.OnNavigatedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private var isLoggedIn = false

    override fun onResume() {
        super.onResume()
        findNavController(R.id.container).addOnNavigatedListener(this)
    }

    override fun onPause() {
        super.onPause()
        findNavController(R.id.container).removeOnNavigatedListener(this)
    }

    override fun onNavigated(controller: NavController, destination: NavDestination) {
        Log.d("MainActivity", "onNavigated : ${destination.label}")
        // java.lang.IllegalStateException: FragmentManager is already executing transactions
        // https://issuetracker.google.com/issues/79632233
        // TODO: 未ログインの場合に LoginFragment へ遷移させたい
//        when (destination.id) {
//            R.id.profileFragment -> if (!isLoggedIn) {
//                findNavController(R.id.container).navigate(R.id.action_login)
//            }
//            R.id.loginFragment -> isLoggedIn = true
//        }
    }
}
