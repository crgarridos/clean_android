package me.cgarrido.cleanandroid.domain

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Single
import me.cgarrido.cleanandroid.domain.model.Song
import me.cgarrido.cleanandroid.domain.repository.SongRepository
import me.cgarrido.cleanandroid.domain.test.factory.SongFactory
import me.cgarrido.cleanandroid.domain.test.utils.toPagedList
import me.cgarrido.cleanandroid.util.executor.TestExecutionSchedulers
import org.junit.Before
import org.junit.Test

class GetSongsTest {

    private val repository = mock<SongRepository>()
    private val schedulers = TestExecutionSchedulers()
    private val getSongs = GetSongs(repository, schedulers)

    private lateinit var songs: List<Song>

    @Before
    fun setup() {
        songs = SongFactory.makeList(5)

        whenever(repository.getAll())
                .thenReturn(Single.just(songs.toPagedList()))
    }

    @Test
    fun retrieveSongs() {
        getSongs.execute(null).test()
                .assertComplete()
                .assertValue(songs.toPagedList())
    }

    @Test
    fun retrieveSongsFromRepository() {
        getSongs.execute(null).test()
        verify(repository).getAll()
    }
}

