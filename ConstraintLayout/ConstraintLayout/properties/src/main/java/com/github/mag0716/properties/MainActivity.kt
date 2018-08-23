package com.github.mag0716.properties

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintProperties
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var top = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // これは適用される
        applyTextProperties()

        // こっちは適用されない
        // View のサイズ、レイアウトが確定した後に変更はできないぽい
        button.setOnClickListener {
            applyTextProperties()
        }
    }

    private fun applyTextProperties() {
        // ConstraintProperties のコンストラクタに ConstraintLayout の子供の View を渡す
        ConstraintProperties(text).apply {
            val bias = if (top) 1.0f else 0.0f
            top = !top
            verticalBias(bias).apply()
        }
    }
}
