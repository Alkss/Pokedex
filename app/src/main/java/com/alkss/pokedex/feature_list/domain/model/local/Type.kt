package com.alkss.pokedex.feature_list.domain.model.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Type(
    @PrimaryKey(autoGenerate = true) val typeId: Int?,
    val name: String
)
