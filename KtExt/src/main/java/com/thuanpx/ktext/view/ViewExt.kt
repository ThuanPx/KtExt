@file:JvmName("KtExtView")
@file:JvmMultifileClass

package com.thuanpx.ktext.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.FontRes
import androidx.core.content.res.ResourcesCompat

/**
 * Created by ThuanPx
 */

fun View.show(isShow: Boolean = true) {
    visibility = if (isShow) View.VISIBLE else View.INVISIBLE
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone(isGone: Boolean = true) {
    visibility = if (isGone) View.GONE else View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.OnClickListener.listenToViews(vararg views: View) {
    views.forEach { it.setOnClickListener(this) }
}

@SuppressLint("ClickableViewAccessibility")
fun View.setOnVeryLongClickListener(timeDelay: Long = 3000, listener: () -> Unit) {
    setOnTouchListener(object : View.OnTouchListener {
        private val handler = Handler(Looper.getMainLooper())
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            if (event?.action == MotionEvent.ACTION_DOWN) {
                handler.postDelayed({
                    listener.invoke()
                }, timeDelay)
            } else if (event?.action == MotionEvent.ACTION_UP) {
                handler.removeCallbacksAndMessages(null)
            }
            return true
        }
    })
}

fun View.showKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    this.requestFocus()
    imm.showSoftInput(this, 0)
}

/**
 * Try to hide the keyboard and returns whether it worked
 * https://stackoverflow.com/questions/1109022/close-hide-the-android-soft-keyboard
 */
fun View.hideKeyboard(): Boolean {
    try {
        val inputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
    } catch (ignored: RuntimeException) { }
    return false
}

/**
 * Extension method to update padding of view.
 *
 */
fun View.updatePadding(paddingStart: Int = getPaddingStart(),
                       paddingTop: Int = getPaddingTop(),
                       paddingEnd: Int = getPaddingEnd(),
                       paddingBottom: Int = getPaddingBottom()) {
    setPaddingRelative(paddingStart, paddingTop, paddingEnd, paddingBottom)
}

/**
 * Extension method to set View's height.
 */
fun View.setHeight(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.height = value
        layoutParams = lp
    }
}

/**
 * Extension method to set View's width.
 */
fun View.setWidth(value: Int) {
    val lp = layoutParams
    lp?.let {
        lp.width = value
        layoutParams = lp
    }
}

/**
 * Extension method to set font for TextView.
 */
fun TextView.setFont(@FontRes font: Int) {
    typeface = ResourcesCompat.getFont(this.context, font)
}