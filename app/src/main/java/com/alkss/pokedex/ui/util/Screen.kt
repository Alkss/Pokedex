package com.alkss.pokedex.ui.util

/**
 * Represents the different screens in the delivery feature.
 *
 * @property route The route associated with the screen.
 */
sealed class Screen(val route: String) {
    /**
     * Represents the home screen.
     */
    data object PokemonList: Screen("home_screen")

    /**
     * Represents the detail screen.
     */
    data object PokemonDetail: Screen("pokemon_detail")
}
