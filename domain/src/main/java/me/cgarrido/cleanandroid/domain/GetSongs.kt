package me.cgarrido.cleanandroid.domain

import io.reactivex.Single
import me.cgarrido.cleanandroid.util.executor.ExecutionSchedulers
import me.cgarrido.cleanandroid.domain.model.Song
import me.cgarrido.cleanandroid.domain.repository.SongRepository
import javax.inject.Inject

open class GetSongs
@Inject constructor(private val songRepository: SongRepository,
                    schedulers: ExecutionSchedulers) : SingleUseCase<Nothing?, List<Song>>(schedulers) {

    override fun execute(params: Nothing?): Single<List<Song>> {
        return songRepository.getAll()
                .subscribeOn(schedulers.networkIO)
                .observeOn(schedulers.mainThread)
    }
}


