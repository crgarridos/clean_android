package me.cgarrido.cleanandroid.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Single
import me.cgarrido.cleanandroid.domain.model.Song
import me.cgarrido.cleanandroid.domain.test.factory.SongFactory
import me.cgarrido.cleanandroid.domain.test.utils.toPagedList
import org.junit.Rule
import org.junit.Test
import java.net.UnknownHostException

class SongRepositoryImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val cache = mock<SongCacheSource>()
    private val remote = mock<SongRemoteSource>()
    private val repository = SongRepositoryImpl(cache, remote)


    @Test
    fun getAll_useCacheIfRemoteIsNotAvailable() {
        val songs = SongFactory.makeList(10)

        stubRemoteGetSongsResponse(Single.error(UnknownHostException()))
        stubCacheHasElements(cache, songs)
        stubCacheIsExpired(cache, false)

        repository.getAll().map { it as List<Song> }.test()
                .assertValue(songs)
    }


    @Test
    fun getAll_cacheSavesRemoteResponseWhenExpired() {
        val songs = SongFactory.makeList(10)

        stubRemoteGetSongsResponse(Single.just(songs))
        stubSaveElements(cache)
        stubCacheIsExpired(cache, true)
        stubCacheHasElements(cache, songs)

        repository.getAll().map { it as List<Song> }.test()
                .assertValue(songs)

        verify(cache).save(eq(songs), any())
    }



    @Test
    fun getAll_throwsRemoteExceptionIfInvalidCache() {
        val songs = SongFactory.makeList(10)
        val exception = UnknownHostException()

        stubRemoteGetSongsResponse(Single.error(exception))
        stubCacheHasElements(cache, songs)
        stubCacheIsExpired(cache, true)

        repository.getAll().test()
                .assertError(exception)
    }


    private fun stubRemoteGetSongsResponse(single: Single<List<Song>>) {
        whenever(remote.getSongs())
                .thenReturn(single)
    }

    private fun stubCacheHasElements(cache: SongCacheSource, songs: List<Song>) {
        val pageSize = if(songs.isEmpty()) 1 else songs.size
        whenever(cache.hasElements())
                .thenReturn(Single.just(songs.isNotEmpty()))
        whenever(cache.getSongs(any()))
                .thenReturn(Single.just(songs.toPagedList(pageSize)))
    }

    private fun stubCacheIsExpired(cache: SongCacheSource, isExpired: Boolean) {
        whenever(cache.isExpired()).thenReturn(Single.just(isExpired))
    }

    private fun stubSaveElements(cache: SongCacheSource) {
        whenever(cache.save(any(), any()))
                .thenReturn(Completable.complete())
    }
}