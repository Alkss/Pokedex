package com.alkss.pokedex.core.data.repository

import com.alkss.pokedex.feature_list.data.data_source.PokemonDao
import com.alkss.pokedex.feature_list.data.data_source.PokemonWithTypeAndMoves
import javax.inject.Inject

class PokemonLocalRepository @Inject constructor(
    private val pokemonDao: PokemonDao,
) {
    fun getAllPokemon(): List<PokemonWithTypeAndMoves> {
        return pokemonDao.getPokemonWithTypeAndMoves()
    }

    fun getPokemonListByName(name: String): List<PokemonWithTypeAndMoves> {
        return pokemonDao.getPokemonListWithTypeAndMovesByName(name)
    }

    fun getPokemonByName(name: String): List<PokemonWithTypeAndMoves> {
        return pokemonDao.getPokemonWithTypeAndMovesByName(name)
    }

    fun insertPokemonList(pokemonList: List<PokemonWithTypeAndMoves>) {
        pokemonList.forEach {
            pokemonDao.insertPokemonWithTypeAndMoves(it.pokemon, it.types, it.moves)
        }
    }

    fun updatePokemon(pokemonName: String, isFavorite: Boolean) {
        val pokemon = pokemonDao.getPokemonWithTypeAndMovesByName(pokemonName)
        val updatePokemon = pokemon.first().pokemon.copy(isFavorite = isFavorite)
        pokemonDao.updatePokemon(updatePokemon)
    }
}
