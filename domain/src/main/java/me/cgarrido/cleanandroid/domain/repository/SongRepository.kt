package me.cgarrido.cleanandroid.domain.repository

import android.arch.paging.PagedList
import io.reactivex.Single
import me.cgarrido.cleanandroid.domain.model.Song

interface SongRepository {
    fun getAll(): Single<PagedList<Song>>
    fun getByAlbum(albumId: Long): Single<List<Song>>
}
