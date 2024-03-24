package com.alkss.pokedex.feature_list.domain.model.local

import androidx.room.Entity

@Entity(primaryKeys = ["pokemonId", "moveId"])
data class PokemonMovesCrossRef(
    val pokemonId: Long,
    val moveId: Long
)
