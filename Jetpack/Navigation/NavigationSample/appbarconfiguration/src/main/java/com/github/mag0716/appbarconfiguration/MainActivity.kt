package com.github.mag0716.appbarconfiguration

import android.os.Bundle
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
        // これだと今まで通り(startDestination 以外は Up Button が表示される)
        //appBarConfiguration = AppBarConfiguration(navController.graph)
        // こっちだと指定した Destination では Up Button が表示されない
        appBarConfiguration = AppBarConfiguration(setOf(R.id.first, R.id.second))
        toolbar.setupWithNavController(navController, appBarConfiguration)
    }
}
