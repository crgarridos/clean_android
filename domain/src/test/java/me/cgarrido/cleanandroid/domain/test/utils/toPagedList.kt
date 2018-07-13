package me.cgarrido.cleanandroid.domain.test.utils

import android.arch.paging.PagedList

fun <E> List<E>.toPagedList(pageSize: Int = size): PagedList<E> {
    val list = this
    return PagedList.Builder(ListDataSource.Factory(list).create(), pageSize)
            .setNotifyExecutor { }
            .setFetchExecutor { }
            .build()
}
