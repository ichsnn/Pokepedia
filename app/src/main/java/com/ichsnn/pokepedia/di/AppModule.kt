package com.ichsnn.pokepedia.di

import com.ichsnn.core.domain.usecase.PokemonInteractor
import com.ichsnn.core.domain.usecase.PokemonUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModule {
    @Binds
    @Singleton
    abstract fun providePokemonUseCase(pokemonInteractor: PokemonInteractor): PokemonUseCase
}