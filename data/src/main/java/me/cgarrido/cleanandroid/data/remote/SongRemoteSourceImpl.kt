package me.cgarrido.cleanandroid.data.remote

import io.reactivex.Single
import me.cgarrido.cleanandroid.data.SongRemoteSource
import me.cgarrido.cleanandroid.data.remote.service.SongService
import me.cgarrido.cleanandroid.domain.model.Song
import javax.inject.Inject

class SongRemoteSourceImpl
@Inject constructor(
        private val songService: SongService) : SongRemoteSource {

    override fun getSongs(): Single<List<Song>> {
        return songService.getSongs()
    }

}