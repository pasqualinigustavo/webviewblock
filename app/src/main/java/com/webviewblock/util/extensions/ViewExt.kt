package com.webviewblock.util.extensions

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.annotation.StyleRes
import androidx.fragment.app.Fragment

fun View.dpToPx(dp: Int): Float {
    return (dp * resources.displayMetrics.density)
}

fun TextView.setStyle(@StyleRes style: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        setTextAppearance(style)
    } else {
        @Suppress("DEPRECATION")
        setTextAppearance(context, style)
    }
}

fun TextView.show(resId: Int) {
    visibility = View.VISIBLE
    setText(resId)
}

fun TextView.hide() {
    visibility = View.GONE
    text = null
}

fun Fragment.hideKeyboard() {
    view?.let { activity?.hideKeyboard(it) }
}

fun Fragment.showKeyboard() {
    view?.let { activity?.showKeyboard(it) }
}

fun Activity.hideKeyboard() {
    hideKeyboard(
        currentFocus ?: View(this)
    )
}

fun Activity.showKeyboard() {
    showKeyboard(
        currentFocus ?: View(this)
    )
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context.showKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.SHOW_IMPLICIT)
}

fun View.show(display: Boolean) {
    if (display) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.GONE
    }
}

fun View.visible(visible: Boolean) {
    if (visible) {
        this.visibility = View.VISIBLE
    } else {
        this.visibility = View.INVISIBLE
    }
}

fun View.toggle() {
    show(visibility != View.VISIBLE)
}

fun TextView.applyAutoSizingInSp(maxTextSize: Int, minTextSize: Int, stepGranularity: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.setAutoSizeTextTypeUniformWithConfiguration(
            minTextSize,
            maxTextSize,
            stepGranularity,
            TypedValue.COMPLEX_UNIT_SP
        )
    }
}
