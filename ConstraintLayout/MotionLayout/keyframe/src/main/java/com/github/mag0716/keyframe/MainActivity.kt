package com.github.mag0716.keyframe

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.motion.widget.MotionLayout
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MotionLayout.TransitionListener, AdapterView.OnItemSelectedListener {

    companion object {
        const val TAG = "keyframe"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        motion_layout.setTransitionListener(this)
        spinner.onItemSelectedListener = this
    }

    override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
        Log.d(TAG, "onTransitionCompleted : currentId = $currentId")
    }

    override fun onTransitionChange(motionLayout: MotionLayout?, startId: Int, endId: Int, progress: Float) {
        Log.d(TAG, "onTransitionChange : startId = $startId, endId = $endId")
    }

    override fun onTransitionTrigger(motionLayout: MotionLayout?, triggerId: Int, positive: Boolean, progress: Float) {
        Log.d(TAG, "onTransitionTrigger : triggerId = $triggerId, positive = $positive, progress = $progress")
    }

    override fun onTransitionStarted(motionLayout: MotionLayout?, startId: Int, endId: Int) {
        Log.d(TAG, "onTransitionStarted : startId = $startId, endId = $endId")
    }

    override fun onNothingSelected(adapter: AdapterView<*>?) {
    }

    override fun onItemSelected(adapter: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val motionName = resources.getStringArray(R.array.items)[position]
        val motionId = resources.getIdentifier(motionName, "xml", packageName)
        motion_layout.loadLayoutDescription(motionId)
    }
}
