package com.alkss.pokedex.feature_list.presentation

sealed class PokemonListScreenEvent {
    data object Refresh : PokemonListScreenEvent()
    data object NextPage: PokemonListScreenEvent()
    data class SearchByName(val pokemonName: String): PokemonListScreenEvent()
}
