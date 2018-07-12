package me.cgarrido.cleanandroid.injection

import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import me.cgarrido.cleanandroid.CleanApplication
import me.cgarrido.cleanandroid.injection.module.DataModule
import me.cgarrido.cleanandroid.injection.module.UIModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    UIModule::class,
    DataModule::class
])
interface AppComponent : AndroidInjector<CleanApplication> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: CleanApplication): Builder

        fun build(): AppComponent
    }

    override fun inject(app: CleanApplication)
}