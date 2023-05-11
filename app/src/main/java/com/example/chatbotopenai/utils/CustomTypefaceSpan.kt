package com.app.utils

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.MetricAffectingSpan


class CustomTypefaceSpan(typeface: Typeface?)  : MetricAffectingSpan() {

    private var typeface: Typeface? = null

    override fun updateDrawState(drawState: TextPaint) {
        apply(drawState)
    }

    override fun updateMeasureState(paint: TextPaint) {
        apply(paint)
    }

    private fun apply(paint: Paint) {
        val oldTypeface: Typeface = paint.typeface
        val oldStyle = oldTypeface.style
        val fakeStyle = oldStyle and typeface!!.style.inv()
        if (fakeStyle and Typeface.BOLD != 0) {
            paint.isFakeBoldText = true
        }
        if (fakeStyle and Typeface.ITALIC != 0) {
            paint.textSkewX = -0.25f
        }
        paint.typeface = typeface
    }
}