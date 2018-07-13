package me.cgarrido.cleanandroid.viewmodel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Single
import me.cgarrido.cleanandroid.domain.GetSongs
import me.cgarrido.cleanandroid.domain.model.Song
import me.cgarrido.cleanandroid.domain.test.factory.SongFactory
import me.cgarrido.cleanandroid.domain.test.utils.toPagedList
import me.cgarrido.cleanandroid.viewmodel.me.cgarrido.cleanandroid.ui.test.utils.testValue
import me.cgarrido.cleanandroid.viewmodel.resources.Result
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test


class SongsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
    val getSongsUseCase = mock<GetSongs>()
    val viewModel = SongsViewModel(getSongsUseCase)


    @Test
    fun retrieveSongsExecutesUseCase() {
        val songs = SongFactory.makeList(5)
        stubGetSongsUseCase(Single.just(songs))
        viewModel.retrieveSongs()
        verify(getSongsUseCase, times(1)).execute(eq(null))
    }


    @Test
    fun retrieveSongsSuccess() {
        val songs = SongFactory.makeList(5)
        stubGetSongsUseCase(Single.just(songs))
        viewModel.retrieveSongs()

        assertEquals(Result.Success(songs), viewModel.songs.testValue)
    }


    @Test
    fun retrieveSongsError() {
        val throwable = Error()
        stubGetSongsUseCase(Single.error(throwable))
        viewModel.retrieveSongs()

        assertEquals(Result.Error<List<Song>>(throwable), viewModel.songs.testValue)
    }

    private fun stubGetSongsUseCase(single: Single<List<Song>>) {
        whenever(getSongsUseCase.execute(null))
                .thenReturn(single.map { it.toPagedList() })
    }

}

