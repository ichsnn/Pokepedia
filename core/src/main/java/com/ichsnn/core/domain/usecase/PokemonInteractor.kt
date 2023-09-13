package com.ichsnn.core.domain.usecase

import com.ichsnn.core.data.Resource
import com.ichsnn.core.domain.model.Pokemon
import com.ichsnn.core.domain.repository.IPokemonRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PokemonInteractor @Inject constructor(private val repository: IPokemonRepository) :
    PokemonUseCase {
    override fun getAllPokemon(): Flow<Resource<List<Pokemon>>> = repository.getAllPokemon()

    override fun getPokemonDetailByName(name: String): Flow<Resource<Pokemon>> =
        repository.getPokemonSpecies(name)

    override fun getFavoritePokemon(): Flow<List<Pokemon>> = repository.getFavouritePokemon()

    override fun setFavoritePokemon(pokemon: Pokemon, isFavorite: Boolean) =
        repository.setFavoritePokemon(pokemon, isFavorite)

}