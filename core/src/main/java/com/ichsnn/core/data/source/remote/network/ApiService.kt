package com.ichsnn.core.data.source.remote.network

import com.ichsnn.core.data.source.remote.response.ListPokemonItemResponse
import com.ichsnn.core.data.source.remote.response.ListResponse
import com.ichsnn.core.data.source.remote.response.PokemonResponse
import com.ichsnn.core.data.source.remote.response.PokemonSpeciesResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("pokemon")
    suspend fun getListPokemon(): ListResponse<ListPokemonItemResponse>

    @GET("pokemon/{id}")
    suspend fun getPokemon(@Path("id") id: String): PokemonResponse

    @GET("pokemon-species/{id}")
    suspend fun getPokemonSpecies(@Path("id") id: String): PokemonSpeciesResponse
}