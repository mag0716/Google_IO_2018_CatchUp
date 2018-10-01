package com.github.mag0716.motionhelper

import android.content.Context
import androidx.constraintlayout.motion.widget.MotionHelper
import android.util.AttributeSet
import android.view.View

class Fade : MotionHelper {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)

    override fun setProgress(view: View, progress: Float) {
        view.alpha = progress
    }
}