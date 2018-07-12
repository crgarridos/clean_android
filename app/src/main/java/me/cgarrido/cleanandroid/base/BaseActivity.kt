package me.cgarrido.cleanandroid.base

import android.support.design.widget.Snackbar
import android.view.View
import dagger.android.support.DaggerAppCompatActivity

abstract class BaseActivity : DaggerAppCompatActivity(){

    fun snackbar(message: String, duration: Int, view: View = findViewById(android.R.id.content)): Snackbar {
        return Snackbar.make(view, message, duration)
    }
}