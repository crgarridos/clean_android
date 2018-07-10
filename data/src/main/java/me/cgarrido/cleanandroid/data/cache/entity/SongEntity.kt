package me.cgarrido.cleanandroid.data.cache.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import me.cgarrido.cleanandroid.data.cache.dao.SongDao

@Entity(tableName = SongDao.Constants.TABLE_NAME)
data class SongEntity(
        @PrimaryKey
        val id: Long,
        val albumId: Long,
        val title: String,
        val url: String,
        val thumbnailUrl: String
)
//{
//    override fun toString(): String {
//        return id.toString()
//    }
//}