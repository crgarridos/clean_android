package me.cgarrido.cleanandroid.data

import io.reactivex.Single
import me.cgarrido.cleanandroid.domain.model.Song

interface SongRemoteSource {
    fun getSongs(): Single<List<Song>>
}