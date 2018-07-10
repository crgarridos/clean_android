package me.cgarrido.cleanandroid.data.cache

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import me.cgarrido.cleanandroid.data.cache.database.AppDatabase
import me.cgarrido.cleanandroid.data.cache.entity.SongEntity
import me.cgarrido.cleanandroid.data.cache.test.factory.SongEntityFactory
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class SongDaoTest {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private val database = Room.inMemoryDatabaseBuilder(RuntimeEnvironment.application, AppDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    @After
    fun closeDb() {
        database.close()
    }

    @Test
    fun getSongs_returnsData() {
        val songs = SongEntityFactory.makeList(10)

        database.getSongEntityDao().apply {
            insertAll(songs)
            getSongs().test()
                    //dao returns the list of entities sorted by id
                    .assertValue(songs.sortedBy(SongEntity::id))
        }
    }

    @Test
    fun count() {
        database.getSongEntityDao().apply {
            count().test().assertValue(0)

            insertAll(SongEntityFactory.makeList(10))

            count().test().assertValue(10)

            insertAll(SongEntityFactory.makeList(15))

            count().test().assertValue(25)
        }
    }
}