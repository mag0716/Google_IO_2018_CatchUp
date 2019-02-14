package com.github.mag0716.valid_dynamic_feature

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.play.core.splitcompat.SplitCompat

class ValidFeatureActivity : AppCompatActivity() {

    override fun attachBaseContext(newBase: Context?) {
        super.attachBaseContext(newBase)
        SplitCompat.install(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_valid_feature)

        val textView = findViewById<TextView>(R.id.text)
        // 別モジュールに依存しているとインストールが成功しない？
        // case1:valid_dynamic_feature で valid_module を implementation する
        // case2:app モジュールで valid_module を api する
        textView.text = "valid_dynamic_feature"
    }
}