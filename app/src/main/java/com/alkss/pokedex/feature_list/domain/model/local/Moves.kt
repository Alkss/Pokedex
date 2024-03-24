package com.alkss.pokedex.feature_list.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Moves(
    @PrimaryKey(autoGenerate = true) val moveId: Int?,
    val name: String
)
