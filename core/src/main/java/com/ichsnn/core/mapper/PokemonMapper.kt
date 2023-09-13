package com.ichsnn.core.mapper

import com.ichsnn.core.data.source.local.entity.PokemonEntity
import com.ichsnn.core.data.source.remote.response.PokemonResponse
import com.ichsnn.core.domain.model.Pokemon
import javax.inject.Inject

open class PokemonMapper @Inject constructor() : Mapper<PokemonEntity, Pokemon, PokemonResponse> {
    override fun mapEntityToDomain(type: PokemonEntity): Pokemon {
        return Pokemon(
            id = type.pokemonId,
            name = type.name,
            imageUrl = type.imageUrl,
            description = type.description,
            isFavorite = type.isFavorite
        )
    }

    override fun mapDomainToEntity(type: Pokemon): PokemonEntity {
        return PokemonEntity(
            type.id,
            name = type.name,
            imageUrl = type.imageUrl,
            description = type.description,
            isFavorite = type.isFavorite
        )
    }

    override fun mapResponseToEntity(type: PokemonResponse): PokemonEntity {
        return PokemonEntity(
            pokemonId = type.id,
            name = type.name,
            imageUrl = type.sprites.frontDefault.toString(),
            description = "",
            isFavorite = false
        )
    }

}