package com.alkss.pokedex.feature_list.data.data_source

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Junction
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Update
import com.alkss.pokedex.feature_list.domain.model.local.Moves
import com.alkss.pokedex.feature_list.domain.model.local.Pokemon
import com.alkss.pokedex.feature_list.domain.model.local.PokemonMovesCrossRef
import com.alkss.pokedex.feature_list.domain.model.local.PokemonTypeCrossRef
import com.alkss.pokedex.feature_list.domain.model.local.Type
import com.alkss.pokedex.feature_list.presentation.PokemonUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

@Dao
interface PokemonDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemon(pokemon: Pokemon): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertType(type: Type): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMoves(moves: Moves): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonTypeCrossRef(crossRef: PokemonTypeCrossRef)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPokemonMovesCrossRef(crossRef: PokemonMovesCrossRef)

    @Transaction
    @Query("SELECT * FROM pokemon")
    fun getPokemonWithTypeAndMoves(): List<PokemonWithTypeAndMoves>

    @Transaction
    @Query("SELECT * FROM pokemon WHERE name LIKE '%' || :name || '%' LIMIT 1")
    fun getPokemonWithTypeAndMovesByName(name: String): List<PokemonWithTypeAndMoves>

    @Transaction
    @Query("SELECT * FROM pokemon WHERE name LIKE '%' || :name || '%'")
    fun getPokemonListWithTypeAndMovesByName(name: String): List<PokemonWithTypeAndMoves>

    @Transaction
    fun insertPokemonWithTypeAndMoves(pokemon: Pokemon, types: List<Type>, moves: List<Moves>) {
        val pokemonId = insertPokemon(pokemon)
        types.forEach { type ->
            val typeId = insertType(type)
            insertPokemonTypeCrossRef(PokemonTypeCrossRef(pokemonId = pokemonId, typeId = typeId))
        }
        moves.forEach { move ->
            val moveId = insertMoves(move)
            insertPokemonMovesCrossRef(PokemonMovesCrossRef(pokemonId = pokemonId, moveId = moveId))
        }
    }

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updatePokemon(pokemon: Pokemon)
}

data class PokemonWithTypeAndMoves(
    @Embedded val pokemon: Pokemon,
    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "typeId",
        associateBy = Junction(PokemonTypeCrossRef::class)
    )
    val types: List<Type>,

    @Relation(
        parentColumn = "pokemonId",
        entityColumn = "moveId",
        associateBy = Junction(PokemonMovesCrossRef::class)
    )
    val moves: List<Moves>
)

fun List<PokemonWithTypeAndMoves>.toUiState(): List<PokemonUiState> {
    val pokemonList = MutableStateFlow<List<PokemonUiState>>(emptyList())

    forEach { unMappedList ->
        pokemonList.update {
            it + PokemonUiState(
                pokemonName = unMappedList.pokemon.name,
                pokemonImageUrl = unMappedList.pokemon.image,
                pokemonNumber = unMappedList.pokemon.order
            )
        }
    }

    return pokemonList.value
}