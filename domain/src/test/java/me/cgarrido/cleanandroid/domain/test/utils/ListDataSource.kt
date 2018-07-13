package me.cgarrido.cleanandroid.domain.test.utils

import android.arch.paging.DataSource
import android.arch.paging.PositionalDataSource
import java.util.*

internal class ListDataSource<T>(list: List<T>) : PositionalDataSource<T>() {

    private val mList: List<T> = ArrayList(list)

    override fun loadInitial(params: PositionalDataSource.LoadInitialParams,
                             callback: PositionalDataSource.LoadInitialCallback<T>) {
        val totalCount = mList.size

        val position = PositionalDataSource.computeInitialLoadPosition(params, totalCount)
        val loadSize = PositionalDataSource.computeInitialLoadSize(params, position, totalCount)

        // for simplicity, we could return everything immediately,
        // but we tile here since it's expected behavior
        val sublist = mList.subList(position, position + loadSize)
        callback.onResult(sublist, position, totalCount)
    }

    override fun loadRange(params: PositionalDataSource.LoadRangeParams,
                           callback: PositionalDataSource.LoadRangeCallback<T>) {
        callback.onResult(mList.subList(params.startPosition,
                params.startPosition + params.loadSize))
    }

    class Factory<T>(private val list: List<T>) : DataSource.Factory<Int, T>() {
        override fun create(): DataSource<Int, T> {
            return ListDataSource(list)
        }
    }
}