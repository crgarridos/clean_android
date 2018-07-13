package me.cgarrido.cleanandroid.domain.test.factory

import me.cgarrido.cleanandroid.domain.model.Song
import me.cgarrido.cleanandroid.util.DataFactory

object SongFactory {
    fun makeSong(id: Long = DataFactory.randomLong()): Song = with(DataFactory) {
        Song(randomLong(), randomLong(), randomString(), randomString(), randomString())
    }

    fun makeList(size: Int): List<Song> {
        return generateSequence(makeSong(1)) {
            it.copy(id = it.id + 1)
        }.take(size).toList()
    }
}