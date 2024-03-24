package com.alkss.pokedex.feature_list.domain.use_case

import android.util.Log
import com.alkss.pokedex.core.NetworkResult
import com.alkss.pokedex.core.data.repository.PokemonLocalRepository
import com.alkss.pokedex.core.data.repository.PokemonRemoteRepository
import com.alkss.pokedex.feature_list.data.data_source.PokemonWithTypeAndMoves
import com.alkss.pokedex.feature_list.domain.model.local.Moves
import com.alkss.pokedex.feature_list.domain.model.local.Pokemon
import com.alkss.pokedex.feature_list.domain.model.local.Type
import com.alkss.pokedex.feature_list.domain.model.remote.PokemonList
import javax.inject.Inject

class FetchPokemonRequest @Inject constructor(
    private val remoteRepository: PokemonRemoteRepository,
    private val localRepository: PokemonLocalRepository
) {
    suspend operator fun invoke(offset: Int): List<PokemonWithTypeAndMoves>? {
        return when (val pokemonResponseList =
            remoteRepository.getPokemonList(limit = 20, offset = offset)) {
            is NetworkResult.Error -> {
                Log.d(
                    "RemoteRepositoryRequest",
                    "Error while trying to get pokemon list}"
                )
                null
            }

            is NetworkResult.Success -> {
                val pokemonList: MutableList<PokemonWithTypeAndMoves> = mutableListOf()
                updatePokemonDetailList(pokemonResponseList, pokemonList)
                pokemonList.toList()
            }
        }
    }

    private suspend fun updatePokemonDetailList(
        pokemonResponseList: NetworkResult.Success<PokemonList>,
        pokemonList: MutableList<PokemonWithTypeAndMoves>
    ): List<PokemonWithTypeAndMoves> {
        pokemonResponseList.data.results.forEach {
            when (val pokemon = remoteRepository.getPokemon(it.name)) {
                is NetworkResult.Error -> {
                    Log.d(
                        "RemoteRepositoryRequest",
                        "Error while trying to get details from ${it.name}"
                    )
                }

                is NetworkResult.Success -> {
                    updatePokemonDetail(pokemon, pokemonList)
                }
            }
        }
        return pokemonList
    }

    private fun updatePokemonDetail(
        pokemon: NetworkResult.Success<com.alkss.pokedex.feature_detail.domain.model.remote.Pokemon>,
        pokemonList: MutableList<PokemonWithTypeAndMoves>
    ) {
        val typeList = mutableListOf<Type>()
        val moveList = mutableListOf<Moves>()

        pokemon.data.typeList.onEach { typeListResponse ->
            typeList.add(Type(typeId = null, name = typeListResponse.type.name))
        }

        pokemon.data.moveList.onEach { move ->
            moveList.add(Moves(moveId = null, name = move.move.name))
        }

        pokemonList.add(
            PokemonWithTypeAndMoves(
                pokemon = Pokemon(
                    pokemonId = pokemon.data.id,
                    order = pokemon.data.id,
                    weight = pokemon.data.weight,
                    name = pokemon.data.name,
                    height = pokemon.data.height,
                    baseXp = pokemon.data.baseXp,
                    image = pokemon.data.image.frontDefault,
                    isFavorite = false
                ),
                types = typeList,
                moves = moveList
            )
        )

        localRepository.insertPokemonList(pokemonList)
    }
}
