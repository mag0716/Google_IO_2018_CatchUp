package com.github.mag0716.sharedelementtransition

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val argument = DetailActivityArgs.fromBundle(intent.extras)
        val item = argument.item

        if (item != null) {
            detailImage.setBackgroundColor(item.color)
            detailText.text = item.name
            detailImage.transitionName = item.name
        }
    }
}