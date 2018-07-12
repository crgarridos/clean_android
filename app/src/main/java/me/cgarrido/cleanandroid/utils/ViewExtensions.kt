package me.cgarrido.cleanandroid.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup


var View.visible: Boolean
    get() =
        visibility == View.VISIBLE

    set(visible) {
        visibility = if(visible) View.VISIBLE else View.GONE
    }

fun ViewGroup.inflate(layoutResId: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutResId, this, attachToRoot)
}