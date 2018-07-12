package me.cgarrido.cleanandroid.test

import android.support.test.InstrumentationRegistry
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import me.cgarrido.cleanandroid.injection.DaggerTestAppComponent
import me.cgarrido.cleanandroid.injection.TestAppComponent

class TestApplication : DaggerApplication() {

    lateinit var appComponent: TestAppComponent
        private set

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        appComponent = DaggerTestAppComponent
                .builder()
                .application(this)
                .build()
        return appComponent
    }


    companion object {
        val appComponent: TestAppComponent
            get() {
                return (InstrumentationRegistry.getTargetContext().applicationContext
                        as TestApplication).appComponent
            }
    }
}