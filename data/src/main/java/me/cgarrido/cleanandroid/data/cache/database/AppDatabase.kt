package me.cgarrido.cleanandroid.data.cache.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import me.cgarrido.cleanandroid.data.cache.dao.SongDao
import me.cgarrido.cleanandroid.data.cache.entity.SongEntity
import javax.inject.Inject

@Database(entities = [SongEntity::class], version = 1)
abstract class AppDatabase
@Inject constructor() : RoomDatabase() {
    abstract fun getSongEntityDao(): SongDao

}