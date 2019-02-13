package com.github.mag0716.dynamic_feature

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.github.mag0716.module.TextProvider
import com.google.android.play.core.splitcompat.SplitCompat

class FeatureActivity : AppCompatActivity() {

    private val textProvider = TextProvider()

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.install(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_feature)

        val textView = findViewById<TextView>(R.id.text)
        textView.text = textProvider.provide()
    }
}