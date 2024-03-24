package com.alkss.pokedex.core.data.remote.endpoint

import com.alkss.pokedex.feature_detail.domain.model.remote.Pokemon
import com.alkss.pokedex.feature_list.domain.model.remote.PokemonList
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokedexApi {

    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int = 20,
        @Query("offset") offset: Int? = null
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemon(@Path("name") name: String): Pokemon
}