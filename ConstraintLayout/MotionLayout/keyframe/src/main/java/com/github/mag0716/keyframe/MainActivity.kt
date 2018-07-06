package com.github.mag0716.keyframe

import android.os.Bundle
import android.support.constraint.motion.MotionLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MotionLayout.TransitionListener, AdapterView.OnItemSelectedListener {

    companion object {
        const val TAG = "keyframe"
    }

    var currentId = R.id.start

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        motion_layout.setTransitionListener(this)
        button.setOnClickListener {
            if (currentId == R.id.start) {
                motion_layout.transitionToEnd()
            } else {
                motion_layout.transitionToStart()
            }
        }
        spinner.onItemSelectedListener = this
    }

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
        Log.d(TAG, "onTransitionCompleted : currentId = $currentId")
        this.currentId = currentId
    }

    override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
        Log.d(TAG, "onTransitionChange : startId = $startId, endId = $endId")
    }

    override fun onNothingSelected(adapter: AdapterView<*>?) {
    }

    override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val motionName = resources.getStringArray(R.array.items)[position]
        val motionId = resources.getIdentifier(motionName, "xml", packageName)
        motion_layout.loadLayoutDescription(motionId)
    }
}
