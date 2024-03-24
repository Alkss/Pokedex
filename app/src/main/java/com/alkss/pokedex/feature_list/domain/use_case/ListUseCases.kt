package com.alkss.pokedex.feature_list.domain.use_case

data class ListUseCases(
    val fetchPokemonRequest: FetchPokemonRequest,
    val getLocalPokemonByName: FetchPokemonListByName
)