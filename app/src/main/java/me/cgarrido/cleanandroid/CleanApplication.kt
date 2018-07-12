package me.cgarrido.cleanandroid

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import me.cgarrido.cleanandroid.injection.DaggerAppComponent
import timber.log.Timber

class CleanApplication : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent
                .builder()
                .application(this)
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }
}