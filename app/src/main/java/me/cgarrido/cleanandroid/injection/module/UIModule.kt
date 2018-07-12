package me.cgarrido.cleanandroid.injection.module

import android.content.Context
import dagger.Binds
import dagger.Module
import me.cgarrido.cleanandroid.application.AppExecutionSchedulers
import me.cgarrido.cleanandroid.application.CleanApplication
import me.cgarrido.cleanandroid.util.executor.ExecutionSchedulers

@Module
interface UIModule {

    @Binds
    fun bindContext(application: CleanApplication): Context

    @Binds
    fun bindExecutionSchedulers(schedulers: AppExecutionSchedulers): ExecutionSchedulers

}
