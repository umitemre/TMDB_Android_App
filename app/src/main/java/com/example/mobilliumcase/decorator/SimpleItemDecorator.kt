package com.example.mobilliumcase.decorator

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.util.TypedValue
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt

class SimpleItemDecorator(private val context: Context, orientation: Int) :
    DividerItemDecoration(context, orientation) {

    override fun onDraw(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val paddingHorizontal = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            16f,
            context.resources.displayMetrics
        ).roundToInt()

        val divider: Drawable = drawable ?: return
        val dividerRight = parent.width - paddingHorizontal
        val childCount = parent.childCount

        for (i in 0 until childCount - 1) {
            // Get child
            val child = parent.getChildAt(i)

            // Get layout params of the child
            val params = child.layoutParams as RecyclerView.LayoutParams
            val dividerTop = child.bottom + params.bottomMargin
            val dividerBottom = dividerTop + divider.intrinsicHeight

            divider.setBounds(paddingHorizontal, dividerTop, dividerRight, dividerBottom)
            divider.draw(c)
        }
    }
}