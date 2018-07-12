package me.cgarrido.cleanandroid.viewmodel.livedata

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import android.arch.lifecycle.Observer

/**
 * LiveData that will not emit `null` values
 */
class NonNullMediatorLiveData<T : Any> : MediatorLiveData<T>()
typealias NonNullLiveData<T> = NonNullMediatorLiveData<T>

fun <T : Any> LiveData<T>.nonNull(): NonNullLiveData<T> {
    val mediator: NonNullLiveData<T> = NonNullLiveData()
    mediator.addSource(this) { it?.let(mediator::setValue) }
    return mediator
}

fun <T : Any> NonNullLiveData<T>.observe(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer { it?.let(observer) })
}

inline fun <T : Any?> LiveData<T>.observe(owner: LifecycleOwner, crossinline observer: (T?) -> Unit) {
    observe(owner, Observer { observer(it) })
}