package com.ichsnn.core.data

import com.ichsnn.core.data.source.local.LocalDataSource
import com.ichsnn.core.data.source.remote.RemoteDataSource
import com.ichsnn.core.data.source.remote.network.ApiResponse
import com.ichsnn.core.data.source.remote.response.PokemonResponse
import com.ichsnn.core.domain.model.Pokemon
import com.ichsnn.core.domain.repository.IPokemonRepository
import com.ichsnn.core.executor.AppExecutor
import com.ichsnn.core.mapper.PokemonMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PokemonRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource,
    private val appExecutor: AppExecutor,
    private val pokemonMapper: PokemonMapper,
) : IPokemonRepository {
    override fun getAllPokemon(): Flow<Resource<List<Pokemon>>> =
        object : NetworkBoundResource<List<Pokemon>, List<PokemonResponse>>() {
            override fun loadFromDB(): Flow<List<Pokemon>> {
                return localDataSource.getAllPokemon().map { listPokemon ->
                    listPokemon.map {
                        pokemonMapper.mapEntityToDomain(it)
                    }
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<List<PokemonResponse>>> = flow {
                val listPokemonItemResponse = remoteDataSource.getListPokemon()
                val listPokemon = mutableListOf<PokemonResponse>()
                listPokemonItemResponse.collect { response ->
                    when (response) {
                        is ApiResponse.Empty -> {
                            emit(ApiResponse.Empty)
                        }

                        is ApiResponse.Error -> {
                            emit(ApiResponse.Error(response.errorMessage))
                        }

                        is ApiResponse.Success -> {
                            response.data.map {
                                when (val pokemonResponse =
                                    remoteDataSource.getPokemon(it.name).first()) {
                                    is ApiResponse.Empty -> {
                                        emit(ApiResponse.Empty)
                                    }

                                    is ApiResponse.Error -> {
                                        emit(ApiResponse.Error(pokemonResponse.errorMessage))
                                    }

                                    is ApiResponse.Success -> {
                                        listPokemon.add(pokemonResponse.data)
                                    }
                                }
                            }
                            emit(ApiResponse.Success(listPokemon))
                        }
                    }
                }
            }

            override suspend fun saveCallResult(data: List<PokemonResponse>) {
                val pokemonList = data.map {
                    pokemonMapper.mapResponseToEntity(it)
                }
                localDataSource.insertPokemon(pokemonList)
            }

            override fun shouldFetch(data: List<Pokemon>?): Boolean = data.isNullOrEmpty()

        }.asFlow()

    override fun getFavouritePokemon(): Flow<List<Pokemon>> {
        return localDataSource.getFavoritePokemon().map { listPokemon ->
            listPokemon.map {
                pokemonMapper.mapEntityToDomain(it)
            }
        }
    }

    override fun setFavoritePokemon(pokemon: Pokemon, isFavorite: Boolean) {
        val pokemonEntity = pokemonMapper.mapDomainToEntity(pokemon)
        appExecutor.diskIO()
            .execute { localDataSource.updateFavoritePokemon(pokemonEntity, isFavorite) }
    }
}