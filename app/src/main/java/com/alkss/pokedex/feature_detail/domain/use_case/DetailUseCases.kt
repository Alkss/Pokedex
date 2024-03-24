package com.alkss.pokedex.feature_detail.domain.use_case

data class DetailUseCases(
    val switchFavorite: SwitchPokemonFavorite,
    val fetchPokemonByName: FetchPokemonByName
)
