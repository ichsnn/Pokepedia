package com.ichsnn.core.data.source.local

import com.ichsnn.core.data.source.local.entity.PokemonEntity
import com.ichsnn.core.data.source.local.room.PokemonDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(private val pokemonDao: PokemonDao) {
    fun getAllPokemon(): Flow<List<PokemonEntity>> = pokemonDao.getAllPokemon()
    suspend fun insertPokemon(data: List<PokemonEntity>) = pokemonDao.insertPokemon(data)
}