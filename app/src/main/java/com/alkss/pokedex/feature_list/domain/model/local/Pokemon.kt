package com.alkss.pokedex.feature_list.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Pokemon(
    @PrimaryKey(autoGenerate = true) val pokemonId: Int,
    val order: Int,
    val weight: Int,
    val name: String,
    val height: Int,
    val baseXp: Int,
    val image: String,
    val isFavorite: Boolean
)

