package me.cgarrido.cleanandroid.data

import android.arch.paging.PagedList
import io.reactivex.Single
import io.reactivex.rxkotlin.zipWith
import me.cgarrido.cleanandroid.domain.model.Song
import me.cgarrido.cleanandroid.domain.repository.SongRepository
import javax.inject.Inject

class SongRepositoryImpl
@Inject constructor(
        private val cache: SongCacheSource,
        private val remote: SongRemoteSource
) : SongRepository {

    override fun getAll(): Single<PagedList<Song>> {
        val pageSize = 50
        return cache.hasElements().zipWith(cache.isExpired())
                .flatMap { (hasElements, isExpired) ->
                    if (hasElements && !isExpired)
                        cache.getSongs(pageSize)
                    else remote.getSongs().flatMap {
                        cache.save(it, System.currentTimeMillis())
                                .andThen(cache.getSongs(pageSize))
                    }
                }
    }

    override fun getByAlbum(albumId: Long): Single<List<Song>> {
        throw UnsupportedOperationException("Getting songs by album isn't supported")
    }
}
