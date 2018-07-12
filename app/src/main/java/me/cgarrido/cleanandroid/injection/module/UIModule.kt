package me.cgarrido.cleanandroid.injection.module

import android.content.Context
import dagger.Binds
import dagger.Module
import me.cgarrido.cleanandroid.AppExecutionSchedulers
import me.cgarrido.cleanandroid.CleanApplication
import me.cgarrido.cleanandroid.util.executor.ExecutionSchedulers

@Module
interface UIModule {

    @Binds
    fun bindContext(application: CleanApplication): Context

    @Binds
    fun bindExecutionSchedulers(uiThread: AppExecutionSchedulers): ExecutionSchedulers

}
