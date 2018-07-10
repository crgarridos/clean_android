package me.cgarrido.cleanandroid.data

import com.nhaarman.mockitokotlin2.*
import io.reactivex.Completable
import io.reactivex.Single
import me.cgarrido.cleanandroid.data.cache.SongCacheSource
import me.cgarrido.cleanandroid.domain.model.Song
import me.cgarrido.cleanandroid.domain.test.factory.SongFactory
import org.junit.Test
import java.net.UnknownHostException

class SongRepositoryImplTest {

    private val cache = mock<SongCacheSource>()
    private val remote = mock<SongRemoteSource>()
    private val repository = SongRepositoryImpl(cache, remote)


    @Test
    fun getAll_useCacheIfRemoteIsNotAvailable() {
        val songs = SongFactory.makeList(10)

        stubRemoteGetSongsResponse(Single.error(UnknownHostException()))
        stubCacheHasElements(cache, songs)
        stubCacheIsExpired(cache, false)

        repository.getAll().test()
                .assertValue(songs)
    }


    @Test
    fun getAll_cacheSavesRemoteResponseWhenExpired() {
        val songs = SongFactory.makeList(10)

        stubRemoteGetSongsResponse(Single.just(songs))
        stubSaveElements(cache)
        stubCacheIsExpired(cache, true)
        stubCacheHasElements(cache, emptyList())

        repository.getAll().test()
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
        whenever(cache.hasElements())
                .thenReturn(Single.just(songs.isNotEmpty()))
        whenever(cache.getSongs())
                .thenReturn(Single.just(songs))
    }

    private fun stubCacheIsExpired(cache: SongCacheSource, isExpired: Boolean) {
        whenever(cache.isExpired()).thenReturn(Single.just(isExpired))
    }

    private fun stubSaveElements(cache: SongCacheSource) {
        whenever(cache.save(any(), any()))
                .thenReturn(Completable.complete())
    }
}