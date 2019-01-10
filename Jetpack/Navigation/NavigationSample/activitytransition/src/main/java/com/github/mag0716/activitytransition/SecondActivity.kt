package com.github.mag0716.activitytransition

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_second.*

/**
 * deep link test
 *
 * adb shell am start -a android.intent.action.VIEW -d "https://com.github.mag0716/data/test"
 *
 */
class SecondActivity : AppCompatActivity() {

    companion object {
        const val DATA = "data"
        fun newIntent(context: Context, data: String): Intent {
            val intent = Intent(context, SecondActivity::class.java)
            intent.putExtra(DATA, data)
            return intent
        }
    }

    private val data by lazy { intent.getStringExtra(DATA) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        Log.d("SecondActivity", "onCreate : $intent, ${intent.getStringExtra(DATA)}")
        when (intent.action) {
            Intent.ACTION_VIEW -> {
                text.text = intent.data?.toString()
            }
            else -> {
                text.text = data
            }
        }

    }
}
