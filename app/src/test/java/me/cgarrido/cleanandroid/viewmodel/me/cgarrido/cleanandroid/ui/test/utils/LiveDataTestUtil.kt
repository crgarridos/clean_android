package me.cgarrido.cleanandroid.viewmodel.me.cgarrido.cleanandroid.ui.test.utils

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Observer
import org.jetbrains.annotations.Nullable
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

object LiveDataTestUtil {

    /**
     * Get the value from a LiveData object. We're waiting for LiveData to emit, for a max of 2 seconds.
     * Once we got a notification via onChanged, we stop observing.
     */
    @Throws(InterruptedException::class)
    fun <T> getValue(liveData: LiveData<T>): T {
        val data = arrayOfNulls<Any>(1)
        val latch = CountDownLatch(1)
        val observer = object : Observer<T> {
            override fun onChanged(@Nullable o: T?) {
                data[0] = o
                latch.countDown()
                liveData.removeObserver(this)
            }
        }
        liveData.observeForever(observer)
        latch.await(2, TimeUnit.SECONDS)

        return data[0] as T
    }
}


inline val <T> LiveData<T>.testValue: T
    get() = LiveDataTestUtil.getValue(this)