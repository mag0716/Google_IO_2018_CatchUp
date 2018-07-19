package com.github.mag0716.decorators

import android.content.Context
import android.graphics.Canvas
import android.support.constraint.ConstraintHelper
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.Log

class MetaballsDecorator @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0)
    : ConstraintHelper(context, attrs, defStyleAttr) {

    private val tag = MetaballsDecorator::class.java.simpleName

    override fun updatePreLayout(container: ConstraintLayout?) {
        super.updatePreLayout(container)
        Log.d(tag, "updatePreLayout")
    }

    override fun updatePostConstraints(constainer: ConstraintLayout?) {
        super.updatePostConstraints(constainer)
        Log.d(tag, "updatePostLayout")
    }

    override fun updatePostMeasure(container: ConstraintLayout?) {
        super.updatePostMeasure(container)
        Log.d(tag, "updatePostMeasure")
    }

    override fun updatePostLayout(container: ConstraintLayout?) {
        Log.d(tag, "updatePostLayout")
        val ids = referencedIds
        for (id in ids) {
            val view = container?.getViewById(id)
            view?.setBackgroundResource(R.drawable.round)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        Log.d(tag, "onDraw")
    }

}