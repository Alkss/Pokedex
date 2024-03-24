package com.alkss.pokedex.feature_list.presentation

data class PokemonListUiState(
    val pokemonList: List<PokemonUiState> = emptyList()
)

data class PokemonUiState(
    val pokemonName: String? = null,
    val pokemonImageUrl: String? = null,
    val pokemonNumber: Int? = null
)