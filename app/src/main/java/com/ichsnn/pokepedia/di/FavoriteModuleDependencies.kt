package com.ichsnn.pokepedia.di

import com.ichsnn.core.domain.usecase.PokemonUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {
    fun pokemonUseCase(): PokemonUseCase
}