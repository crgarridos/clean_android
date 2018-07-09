package me.cgarrido.cleanandroid.domain

import io.reactivex.Completable
import io.reactivex.Single
import me.cgarrido.cleanandroid.util.executor.ExecutionSchedulers

abstract class SingleUseCase<in Params, Result>(protected val schedulers: ExecutionSchedulers) {
    abstract fun execute(params: Params): Single<Result>
}

abstract class CompletableUseCase<in Params>(protected val schedulers: ExecutionSchedulers) {
    abstract fun execute(params: Params): Completable
}

