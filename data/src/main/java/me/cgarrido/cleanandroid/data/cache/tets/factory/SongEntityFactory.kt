package me.cgarrido.cleanandroid.data.cache.tets.factory

import me.cgarrido.cleanandroid.data.cache.entity.SongEntity
import me.cgarrido.cleanandroid.util.DataFactory

object SongEntityFactory {

    fun makeSongEntity(): SongEntity = with(DataFactory) {
        SongEntity(randomLong(), randomLong(), randomString(), randomString(), randomString())
    }

    fun makeList(size: Int): List<SongEntity> {
        return generateSequence(::makeSongEntity)
                .take(size).toList()
    }

}
