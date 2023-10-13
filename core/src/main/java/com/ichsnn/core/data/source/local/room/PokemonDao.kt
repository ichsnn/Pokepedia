package com.ichsnn.core.data.source.local.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.ichsnn.core.data.source.local.entity.PokemonEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PokemonDao {
    @Query("SELECT * FROM pokemon WHERE is_searched = 0")
    fun getAllPokemon(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon WHERE is_favorite = 1")
    fun getFavoritePokemon(): Flow<List<PokemonEntity>>

    @Query("SELECT * FROM pokemon WHERE name = :name")
    fun getPokemonByName(name: String): Flow<PokemonEntity>

    @Update
    fun updateFavoritePokemon(pokemonEntity: PokemonEntity)

    @Query("UPDATE pokemon SET description = :newDescription WHERE name = :name")
    suspend fun updateDescriptionPokemonByName(name: String, newDescription: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemon(data: List<PokemonEntity>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addPokemon(data: PokemonEntity)
}