package me.cgarrido.cleanandroid.domain.repository

import io.reactivex.Single
import me.cgarrido.cleanandroid.domain.model.Song

interface SongRepository {
    fun getAll(): Single<List<Song>>
    fun getByAlbum(albumId: Long): Single<List<Song>>
}
