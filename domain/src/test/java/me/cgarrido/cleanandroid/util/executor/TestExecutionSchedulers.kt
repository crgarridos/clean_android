package me.cgarrido.cleanandroid.util.executor

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class TestExecutionSchedulers
@Inject constructor() : ExecutionSchedulers {

    override val networkIO: Scheduler
        get() = Schedulers.trampoline()

    override val diskIO: Scheduler
        get() = Schedulers.trampoline()

    override val common: Scheduler
        get() = Schedulers.trampoline()

    override val mainThread: Scheduler
        get() = Schedulers.trampoline()
}