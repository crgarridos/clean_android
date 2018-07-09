package me.cgarrido.cleanandroid.domain.model

data class Song(
        val id: Long,
        val albumId: Long,
        val title: String,
        val url: String,
        val thumbnailUrl: String
)