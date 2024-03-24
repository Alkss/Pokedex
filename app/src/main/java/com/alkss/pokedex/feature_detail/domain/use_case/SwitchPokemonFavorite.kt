package com.alkss.pokedex.feature_detail.domain.use_case

import com.alkss.pokedex.core.data.repository.PokemonLocalRepository
import javax.inject.Inject

class SwitchPokemonFavorite @Inject constructor(
    private val localRepository: PokemonLocalRepository
) {
    operator fun invoke(pokemonName: String, isFavorite: Boolean) {
        localRepository.updatePokemon(pokemonName = pokemonName, isFavorite = isFavorite)
    }
}
