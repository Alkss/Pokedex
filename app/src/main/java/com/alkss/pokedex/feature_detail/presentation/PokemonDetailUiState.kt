package com.alkss.pokedex.feature_detail.presentation

data class PokemonDetailUiState(
    val name: String? = null,
    val id: Int? = null,
    val weight: Int? = null,
    val image: String? = null,
    val baseXp: Int? = null,
    val height: Int? = null,
    val isFavorite: Boolean = false,
    val moveList: List<Move> = emptyList(),
    val typeList: List<Type> = emptyList()
)

data class Move(
    val name: String
)

data class Type(
    val name: String
)