package me.cgarrido.cleanandroid.injection.module

import android.content.Context
import dagger.Binds
import dagger.Module
import me.cgarrido.cleanandroid.test.TestApplication
import me.cgarrido.cleanandroid.util.executor.ExecutionSchedulers
import me.cgarrido.cleanandroid.util.executor.TestExecutionSchedulers

@Module
interface TestAppModule {

    @Binds
    fun bindContext(application: TestApplication): Context

    @Binds
    fun bindExecutionSchedulers(schedulers: TestExecutionSchedulers): ExecutionSchedulers

}
