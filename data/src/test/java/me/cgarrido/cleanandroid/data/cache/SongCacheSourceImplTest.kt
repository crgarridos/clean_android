package me.cgarrido.cleanandroid.data.cache

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import me.cgarrido.cleanandroid.data.cache.database.AppDatabase
import me.cgarrido.cleanandroid.data.cache.mapper.SongEntityMapper
import me.cgarrido.cleanandroid.domain.model.Song
import me.cgarrido.cleanandroid.domain.test.factory.SongFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config


@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class SongCacheSourceImplTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    val database = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    val mapper = SongEntityMapper()
    val prefs = AppPreferences(RuntimeEnvironment.application.applicationContext)
    val cache = SongCacheSourceImpl(database.getSongEntityDao(), mapper, prefs)

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun isExpired() {
        cache.isExpired().test()
                .assertValue(true)

        cache.save(emptyList(), System.currentTimeMillis()).test()
                .assertComplete()

        cache.isExpired().test()
                .assertValue(false)

        cache.save(emptyList(), 1000L).test()
                .assertComplete()

        cache.isExpired().test()
                .assertValue(true)
    }

    @Test
    fun hasElements() {
        cache.hasElements().test()
                .assertValue(false)

        cache.save(listOf(SongFactory.makeSong()), 1000L).test()
                .assertComplete()

        cache.hasElements().test()
                .assertValue(true)
    }

    @Test
    fun getSongs() {
        val pageSize = 10

        cache.getSongs(pageSize)
                .map { it as List<Song> }
                .test()
                .assertValue(emptyList())

        val songs = SongFactory.makeList(pageSize)
        cache.save(songs, 1000L).blockingAwait()

        cache.getSongs(pageSize)
                .map { it as List<Song> }
                .test()
                .assertValue(songs)
    }

    @Test
    fun save() {
        val songs = SongFactory.makeList(10)
        cache.save(songs, System.currentTimeMillis()).test()
                .assertComplete()

    }
}