package com.github.mag0716.keyframe

import android.os.Bundle
import android.support.constraint.motion.MotionLayout
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MotionLayout.TransitionListener {

    companion object {
        const val TAG = "keyframe"
    }

    var currentId = R.id.start

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        motion_layout.setTransitionListener(this)
    }

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
        Log.d(TAG, "onTransitionCompleted : currentId = $currentId")
        this.currentId = currentId
    }

    override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
        Log.d(TAG, "onTransitionChange : startId = $startId, endId = $endId")
    }
}
