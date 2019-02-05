package com.github.mag0716.appbundlessample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)
        button.setOnClickListener {
            launchFeatureModule()
        }
    }

    private fun launchFeatureModule() {
        val intent = Intent(Intent.ACTION_VIEW).setClassName(
                "com.github.mag0716.dynamic_feature",
                "com.github.mag0716.dynamic_feature.FeatureActivity"
        )
        startActivity(intent)
    }
}
