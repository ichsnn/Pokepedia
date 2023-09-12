package com.ichsnn.core.mapper

interface Mapper<Entity, Model, Response> {
    fun mapEntityToDomain(type: Entity): Model
    fun mapDomainToEntity(type: Model): Entity
    fun mapResponseToEntity(type: Response): Entity
}