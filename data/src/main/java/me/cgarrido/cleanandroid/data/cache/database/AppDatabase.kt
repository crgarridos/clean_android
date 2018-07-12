package me.cgarrido.cleanandroid.data.cache.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import me.cgarrido.cleanandroid.data.cache.dao.SongDao
import me.cgarrido.cleanandroid.data.cache.entity.SongEntity
import javax.inject.Inject

@Database(entities = [SongEntity::class], version = 1)
abstract class AppDatabase
@Inject constructor() : RoomDatabase() {
    abstract fun getSongEntityDao(): SongDao

    companion object {

        private var INSTANCE: AppDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            if (INSTANCE == null) {
                synchronized(lock) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                AppDatabase::class.java, "clean-android.db")
                                .build()
                    }
                    return INSTANCE as AppDatabase
                }
            }
            return INSTANCE as AppDatabase
        }
    }

}