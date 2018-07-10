package me.cgarrido.cleanandroid.data.cache.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Single
import me.cgarrido.cleanandroid.data.cache.entity.SongEntity


@Dao
interface SongDao {
    @Query("SELECT * FROM song")
    fun getSongs(): Single<List<SongEntity>>

    @Query("SELECT count(*) FROM song")
    fun count(): Single<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(songs: List<SongEntity>)

    object Constants {
        const val TABLE_NAME = "song"
    }
}