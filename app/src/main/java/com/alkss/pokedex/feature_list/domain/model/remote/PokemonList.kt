package com.alkss.pokedex.feature_list.domain.model.remote

import com.google.gson.annotations.SerializedName

data class PokemonList(
    @SerializedName("results")
    val results: List<Result>
)

data class Result(
    @SerializedName("name")
    val name: String
)