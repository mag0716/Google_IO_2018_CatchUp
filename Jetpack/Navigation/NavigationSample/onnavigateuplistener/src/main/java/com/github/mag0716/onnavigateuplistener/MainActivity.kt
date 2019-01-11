package com.github.mag0716.onnavigateuplistener

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.container)
        appBarConfiguration = AppBarConfiguration.Builder(setOf(R.id.first, R.id.second))
                .setFallbackOnNavigateUpListener {
                    Log.d("xxx", "OnNavigateUpListener");
                    true
                }
                .build()
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}
