package com.github.mag0716.keycycle

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.AdapterView

// TODO: Spinner が一度でアニメーションすると表示されなくなる
// MotionLayout 内にある View は全て MotionScene 内で Constraint を指定する必要がある？
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // KeyCycle の waveShape の動きを確認するため、MotionScene ファイルを Spinner で変更可能にする
        spinner.onItemSelectedListener = this
    }

    override fun onNothingSelected(adapter: AdapterView<*>?) {
    }

    override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val motionName = resources.getStringArray(R.array.items)[position]
        val motionId = resources.getIdentifier(motionName, "xml", packageName)
        motion_layout.loadLayoutDescription(motionId)
    }
}
