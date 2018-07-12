package me.cgarrido.cleanandroid.injection.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.cgarrido.cleanandroid.SongsActivity
import me.cgarrido.cleanandroid.injection.scope.ActivityScope

@Module
interface ActivityBindingModule {

    @ActivityScope
    @ContributesAndroidInjector(modules = [])
    fun contributeInjectorForSongsActivity(): SongsActivity

}