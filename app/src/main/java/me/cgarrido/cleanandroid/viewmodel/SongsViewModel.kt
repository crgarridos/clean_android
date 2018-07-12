package me.cgarrido.cleanandroid.viewmodel

import android.arch.lifecycle.MutableLiveData
import io.reactivex.rxkotlin.plusAssign
import me.cgarrido.cleanandroid.base.BaseViewModel
import me.cgarrido.cleanandroid.domain.GetSongs
import me.cgarrido.cleanandroid.domain.model.Song
import me.cgarrido.cleanandroid.viewmodel.livedata.NonNullLiveData
import me.cgarrido.cleanandroid.viewmodel.livedata.nonNull
import me.cgarrido.cleanandroid.viewmodel.resources.Result
import javax.inject.Inject

class SongsViewModel
@Inject constructor(private val getSongs: GetSongs) : BaseViewModel() {

    private val _songs = MutableLiveData<Result<List<Song>>>()
    val songs: NonNullLiveData<Result<List<Song>>>
            get() = _songs.nonNull()

    fun retrieveSongs() {
        _songs.value = Result.Loading()

        disposables += getSongs.execute(null)
                .map { Result.Success(it) as Result<List<Song>> }
                .onErrorReturn { Result.Error(it) }
                .subscribe(_songs::setValue)
    }

}
