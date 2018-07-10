package me.cgarrido.cleanandroid.data.cache.mapper

import me.cgarrido.cleanandroid.data.cache.entity.SongEntity
import me.cgarrido.cleanandroid.domain.model.Song
import javax.inject.Inject

class SongEntityMapper
@Inject constructor() : EntityMapper<SongEntity, Song> {
    override fun mapFromEntity(entity: SongEntity): Song {
        return with(entity) {
            Song(id, albumId, title, url, thumbnailUrl)
        }
    }

    override fun mapToEntity(model: Song): SongEntity {
        return with(model) {
            SongEntity(id, albumId, title, url, thumbnailUrl)
        }
    }
}