package com.ichsnn.core.domain.usecase

import com.ichsnn.core.data.Resource
import com.ichsnn.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface PokemonUseCase {
    fun getAllPokemon(): Flow<Resource<List<Pokemon>>>
    fun getPokemonDetailByName(name: String): Flow<Resource<Pokemon>>
    fun getFavoritePokemon(): Flow<List<Pokemon>>
    fun searchPokemon(name: String): Flow<Resource<Pokemon>>
    fun setFavoritePokemon(pokemon: Pokemon, isFavorite: Boolean)
}