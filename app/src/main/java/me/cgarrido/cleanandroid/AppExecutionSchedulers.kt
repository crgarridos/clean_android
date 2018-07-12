package me.cgarrido.cleanandroid

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.cgarrido.cleanandroid.util.executor.ExecutionSchedulers

class AppExecutionSchedulers : ExecutionSchedulers {

    override val networkIO: Scheduler
        get() = Schedulers.io()

    override val diskIO: Scheduler
        get() = Schedulers.io()

    override val common: Scheduler
        get() = Schedulers.computation()

    override val mainThread: Scheduler
        get() = AndroidSchedulers.mainThread()
}