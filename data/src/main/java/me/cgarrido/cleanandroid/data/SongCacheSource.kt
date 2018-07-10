package me.cgarrido.cleanandroid.data

import io.reactivex.Completable
import io.reactivex.Single
import me.cgarrido.cleanandroid.domain.model.Song

interface SongCacheSource {
    fun isExpired(): Single<Boolean>
    fun hasElements(): Single<Boolean>
    fun getSongs(): Single<List<Song>>
    fun save(songs: List<Song>, timestamp: Long): Completable
    fun updateLastCacheTimestamp(): Completable

}