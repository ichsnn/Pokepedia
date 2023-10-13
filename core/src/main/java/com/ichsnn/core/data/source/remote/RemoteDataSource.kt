package com.ichsnn.core.data.source.remote

import com.ichsnn.core.data.source.remote.network.ApiResponse
import com.ichsnn.core.data.source.remote.network.ApiService
import com.ichsnn.core.data.source.remote.response.ListPokemonItemResponse
import com.ichsnn.core.data.source.remote.response.PokemonResponse
import com.ichsnn.core.data.source.remote.response.PokemonSpeciesResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun getListPokemon(): Flow<ApiResponse<List<ListPokemonItemResponse>>> {
        return flow {
            try {
                val response = apiService.getListPokemon()
                val dataArray = response.results
                if (dataArray.isNotEmpty()) {
                    emit(ApiResponse.Success(response.results))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPokemon(id: String): Flow<ApiResponse<PokemonResponse>> {
        return flow {
            try {
                val pokemon = apiService.getPokemon(id)
                emit(ApiResponse.Success(pokemon))
            } catch (e: HttpException) {
                if (e.code() == 404) {
                    emit(ApiResponse.Error("Pokemon Not Found!"))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getPokemonSpecies(id: String): Flow<ApiResponse<PokemonSpeciesResponse>> {
        return flow {
            try {
                val pokemonSpecies = apiService.getPokemonSpecies(id)
                emit(ApiResponse.Success(pokemonSpecies))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }
}