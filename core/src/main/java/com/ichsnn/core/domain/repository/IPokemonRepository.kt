package com.ichsnn.core.domain.repository

import com.ichsnn.core.data.Resource
import com.ichsnn.core.domain.model.Pokemon
import kotlinx.coroutines.flow.Flow

interface IPokemonRepository {
    fun getAllPokemon(): Flow<Resource<List<Pokemon>>>
    fun getPokemonSpecies(id: String): Flow<Resource<Pokemon>>
    fun getFavouritePokemon(): Flow<List<Pokemon>>
    fun setFavoritePokemon(pokemon: Pokemon, isFavorite: Boolean)
}