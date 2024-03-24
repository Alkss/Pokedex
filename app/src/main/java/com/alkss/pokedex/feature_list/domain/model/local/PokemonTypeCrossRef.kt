package com.alkss.pokedex.feature_list.domain.model.local

import androidx.room.Entity

@Entity(primaryKeys = ["pokemonId", "typeId"])
data class PokemonTypeCrossRef(
    val pokemonId: Long,
    val typeId: Long
)
