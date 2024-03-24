package com.alkss.pokedex.feature_list.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import com.alkss.pokedex.feature_list.data.data_source.PokemonDatabase.Companion.DATABASE_VERSION
import com.alkss.pokedex.feature_list.domain.model.local.Moves
import com.alkss.pokedex.feature_list.domain.model.local.Pokemon
import com.alkss.pokedex.feature_list.domain.model.local.PokemonMovesCrossRef
import com.alkss.pokedex.feature_list.domain.model.local.PokemonTypeCrossRef
import com.alkss.pokedex.feature_list.domain.model.local.Type

@Database(
    entities = [Pokemon::class, Type::class, Moves::class, PokemonTypeCrossRef::class, PokemonMovesCrossRef::class],
    version = DATABASE_VERSION
)
abstract class PokemonDatabase : RoomDatabase() {

    abstract val pokemonDao: PokemonDao

    companion object {
        const val DATABASE_NAME = "pokemon_db"
        const val DATABASE_VERSION = 3
    }
}