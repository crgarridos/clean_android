package me.cgarrido.cleanandroid.data.cache

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.paging.RxPagedListBuilder
import android.arch.persistence.room.Room
import me.cgarrido.cleanandroid.data.cache.database.AppDatabase
import me.cgarrido.cleanandroid.data.cache.entity.SongEntity
import me.cgarrido.cleanandroid.data.cache.test.factory.SongEntityFactory
import me.cgarrido.cleanandroid.util.DataFactory
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

            RxPagedListBuilder(getSongs(), 5).buildObservable()
                    .test()
                    .assertValue {
                        //dao returns the list of entities sorted by id
                        val sortedSongs = songs.sortedBy(SongEntity::id)
                        it.map { it in sortedSongs }
                                .all { true }
                    }
        }
    }


    @Test
    fun getSongs_pageSize() {
        val songs = SongEntityFactory.makeList(500)

        database.getSongEntityDao().apply {
            insertAll(songs)

            repeat(10) {

                val pageSize: Int = DataFactory.randomInt(5..50)
                RxPagedListBuilder(getSongs(), pageSize)
                        .buildObservable()
                        .map { it.filterNotNull() }
                        .map { it.size }
                        .test()
                        .assertValue(pageSize * 3)
            }
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