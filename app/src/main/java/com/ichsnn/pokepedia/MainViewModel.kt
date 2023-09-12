package com.ichsnn.pokepedia

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ichsnn.core.data.PokemonRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(pokemonRepository: PokemonRepository) : ViewModel() {
    val pokemon = pokemonRepository.getAllPokemon().asLiveData()
}