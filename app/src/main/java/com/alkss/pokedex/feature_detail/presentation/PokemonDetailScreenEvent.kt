package com.alkss.pokedex.feature_detail.presentation

sealed class PokemonDetailScreenEvent {
    data object SwitchFavorite : PokemonDetailScreenEvent()
}
