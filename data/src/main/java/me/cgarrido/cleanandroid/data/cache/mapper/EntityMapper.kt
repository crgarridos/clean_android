package me.cgarrido.cleanandroid.data.cache.mapper

interface EntityMapper<Entity, Model> {
    fun mapFromEntity(entity: Entity): Model
    fun mapToEntity(model: Model): Entity
}