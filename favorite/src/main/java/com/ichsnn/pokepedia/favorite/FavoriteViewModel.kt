package com.ichsnn.pokepedia.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ichsnn.core.domain.usecase.PokemonUseCase
import javax.inject.Inject

class FavoriteViewModel @Inject constructor(pokemonUseCase: PokemonUseCase) : ViewModel() {
    val favoritePokemon = pokemonUseCase.getFavoritePokemon().asLiveData()
}