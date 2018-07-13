package me.cgarrido.cleanandroid.domain

import android.arch.paging.PagedList
import io.reactivex.Single
import me.cgarrido.cleanandroid.domain.model.Song
import me.cgarrido.cleanandroid.domain.repository.SongRepository
import me.cgarrido.cleanandroid.util.executor.ExecutionSchedulers
import javax.inject.Inject

open class GetSongs
@Inject constructor(private val songRepository: SongRepository,
                    schedulers: ExecutionSchedulers) : SingleUseCase<Nothing?, PagedList<Song>>(schedulers) {

    override fun execute(params: Nothing?): Single<PagedList<Song>> {
        return songRepository.getAll()
                .subscribeOn(schedulers.networkIO)
                .observeOn(schedulers.mainThread)
    }
}


