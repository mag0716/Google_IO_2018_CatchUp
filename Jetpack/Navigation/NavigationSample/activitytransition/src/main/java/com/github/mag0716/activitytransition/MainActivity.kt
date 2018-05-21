package com.github.mag0716.activitytransition

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button.setOnClickListener {
            // global Action を使って遷移を試そうとしたが、そもそも NavController を取得する方法がなさそう
            //startActivity(SecondActivity.newIntent(this, "move from MainActivity"))
            // NavHostFragment を持っていたら、そこから NavController を取得することはできる
            container.findNavController().navigate(R.id.action_mainFragment_to_secondActivity, bundleOf(SecondActivity.DATA to "move from MainActivity"))
        }
    }
}
