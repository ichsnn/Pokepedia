package com.ichsnn.pokepedia.presentation.findpokemon

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.ichsnn.core.domain.usecase.PokemonUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class FindPokemonViewModel @Inject constructor(private val pokemonUseCase: PokemonUseCase) :
    ViewModel() {

    val searchQuery = MutableStateFlow("")

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    val search = searchQuery.debounce(300).distinctUntilChanged().filter { it.trim().isNotEmpty() }
        .flatMapLatest {
            pokemonUseCase.searchPokemon(it.lowercase(Locale.ROOT))
        }.asLiveData()
}