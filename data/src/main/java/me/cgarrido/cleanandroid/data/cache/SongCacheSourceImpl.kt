package me.cgarrido.cleanandroid.data.cache

import android.arch.paging.PagedList
import android.arch.paging.RxPagedListBuilder
import io.reactivex.Completable
import io.reactivex.Single
import me.cgarrido.cleanandroid.data.SongCacheSource
import me.cgarrido.cleanandroid.data.cache.dao.SongDao
import me.cgarrido.cleanandroid.data.cache.mapper.SongEntityMapper
import me.cgarrido.cleanandroid.domain.model.Song
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class SongCacheSourceImpl
@Inject constructor(
        private val dao: SongDao,
        private val mapper: SongEntityMapper,
        preferences: AppPreferences) : SongCacheSource {

    private var lastUpdateTimeStamp: Long by pref("song_cache_timestamp", 0, preferences)

    override fun isExpired(): Single<Boolean> {
        return Single.fromCallable {
            System.currentTimeMillis() - lastUpdateTimeStamp > TimeUnit.HOURS.toMillis(3)
        }
    }

    override fun hasElements(): Single<Boolean> {
        return dao.count().map { it > 0 }
    }

    override fun getSongs(pageSize: Int): Single<PagedList<Song>> {
        return RxPagedListBuilder(dao.getSongs().map(mapper::mapFromEntity), pageSize)
                .buildObservable()
                .take(1)
                .singleOrError()
    }

    override fun save(songs: List<Song>, timestamp: Long): Completable {
        return Completable.fromAction {
            dao.insertAll(songs.map(mapper::mapToEntity))
            lastUpdateTimeStamp = timestamp
        }
    }
}