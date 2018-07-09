package me.cgarrido.cleanandroid.util.executor

import io.reactivex.Scheduler

interface ExecutionSchedulers {
    val networkIO: Scheduler
    val diskIO: Scheduler
    val common: Scheduler
    val mainThread: Scheduler
}