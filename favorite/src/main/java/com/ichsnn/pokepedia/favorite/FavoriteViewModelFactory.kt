package com.ichsnn.pokepedia.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ichsnn.core.domain.usecase.PokemonUseCase
import javax.inject.Inject

class FavoriteViewModelFactory @Inject constructor(
    private val pokemonUseCase: PokemonUseCase
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass != FavoriteViewModel::class.java) {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
        return FavoriteViewModel(pokemonUseCase) as T
    }
}