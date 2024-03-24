package com.alkss.pokedex.feature_list.domain.use_case

import android.util.Log
import com.alkss.pokedex.core.NetworkResult
import com.alkss.pokedex.core.data.repository.PokemonLocalRepository
import com.alkss.pokedex.core.data.repository.PokemonRemoteRepository
import com.alkss.pokedex.feature_list.data.data_source.PokemonWithTypeAndMoves
import com.alkss.pokedex.feature_list.domain.model.local.Moves
import com.alkss.pokedex.feature_list.domain.model.local.Pokemon
import com.alkss.pokedex.feature_list.domain.model.local.Type
import javax.inject.Inject

class FetchPokemonListByName @Inject constructor(
    private val remoteRepository: PokemonRemoteRepository,
    private val localRepository: PokemonLocalRepository
) {

    suspend operator fun invoke(pokemonName: String): List<PokemonWithTypeAndMoves> {
        val list = localRepository.getPokemonListByName(pokemonName)
        return list.ifEmpty {
            getPokemonFromApi(pokemonName)
        }
    }


    private suspend fun getPokemonFromApi(name: String): List<PokemonWithTypeAndMoves> {
       return when (val response = remoteRepository.getPokemon(name)){
            is NetworkResult.Error -> {
                Log.d(
                    "RemoteRepositoryRequest",
                    "Error while trying to get pokemon $name: ${response.exception.message}"
                )
                emptyList()
            }

            is NetworkResult.Success -> {
                val pokemonList = mutableListOf<PokemonWithTypeAndMoves>()

                val typeList = mutableListOf<Type>()
                val moveList = mutableListOf<Moves>()

                response.data.typeList.onEach { typeListResponse ->
                    typeList.add(Type(typeId = null, name = typeListResponse.type.name))
                }

                response.data.moveList.onEach { move ->
                    moveList.add(Moves(moveId = null, name = move.move.name))
                }

                pokemonList.add(
                    PokemonWithTypeAndMoves(
                        pokemon = Pokemon(
                            pokemonId = response.data.id,
                            order = response.data.id,
                            weight = response.data.weight,
                            name = response.data.name,
                            height = response.data.height,
                            baseXp = response.data.baseXp,
                            image = response.data.image.frontDefault,
                            isFavorite = false
                        ),
                        types = typeList,
                        moves = moveList
                    )
                )

                pokemonList
            }
        }
    }
}
