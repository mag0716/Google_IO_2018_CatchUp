package com.github.mag0716.blankdestination

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NavController.OnNavigatedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            findNavController(R.id.container).navigate(R.id.action_count)
        }
    }

    override fun onResume() {
        super.onResume()
        findNavController(R.id.container).addOnNavigatedListener(this)
    }

    override fun onPause() {
        super.onPause()
        findNavController(R.id.container).removeOnNavigatedListener(this)
    }

    override fun onNavigated(controller: NavController, destination: NavDestination) {
        button.visibility = if (destination.id == R.id.blankFragment) View.VISIBLE else View.INVISIBLE
    }
}
