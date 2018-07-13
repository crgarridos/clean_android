package me.cgarrido.cleanandroid.data

import android.arch.paging.PagedList
import io.reactivex.Completable
import io.reactivex.Single
import me.cgarrido.cleanandroid.domain.model.Song

interface SongCacheSource {
    fun isExpired(): Single<Boolean>
    fun hasElements(): Single<Boolean>
    fun getSongs(pageSize: Int): Single<PagedList<Song>>
    fun save(songs: List<Song>, timestamp: Long): Completable
}