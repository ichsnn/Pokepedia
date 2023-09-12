package com.ichsnn.core.mapper

interface Mapper<Entity, Model, Response> {
    fun mapEntityToDomain(type: Entity): Model
    fun mapModelToEntity(type: Model): Entity
    fun mapResponseToEntity(type: Response): Entity
}