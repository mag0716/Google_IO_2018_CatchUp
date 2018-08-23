package com.github.mag0716.properties

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintProperties
import androidx.constraintlayout.widget.ConstraintsChangedListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = MainActivity::class.java.simpleName
    }

    var defaultConstraintSet = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // setState を使うために必要
        root.loadLayoutDescription(R.xml.constraint_state)

        button.setOnClickListener {
            toggleState()
        }

        root.setOnConstraintsChanged(object : ConstraintsChangedListener() {
            override fun preLayoutChange(stateId: Int, constraintId: Int) {
                super.preLayoutChange(stateId, constraintId)
                Log.d(TAG, "preLayoutChange : $stateId, $constraintId")
            }

            override fun postLayoutChange(stateId: Int, constraintId: Int) {
                super.postLayoutChange(stateId, constraintId)
                Log.d(TAG, "postLayoutChange : $stateId, $constraintId")
                if (stateId == R.id.state_another) {
                    applyTextProperties()
                }
            }
        })
    }

    private fun applyTextProperties() {
        Log.d(TAG, "applyTextProperties : ${checkBox.isChecked}")
        if (checkBox.isChecked) {
            // ConstraintProperties のコンストラクタに ConstraintLayout の子供の View を渡す
            ConstraintProperties(text).apply {
                verticalBias(1.0f).apply()
            }
        }
    }

    private fun toggleState() {
        val constraintId = if (defaultConstraintSet) R.id.state_another else R.id.state_default
        root.setState(constraintId, root.width, root.height)
        defaultConstraintSet = !defaultConstraintSet
    }
}
