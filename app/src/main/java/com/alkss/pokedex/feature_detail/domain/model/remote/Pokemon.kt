package com.alkss.pokedex.feature_detail.domain.model.remote

import com.google.gson.annotations.SerializedName

data class Pokemon(
    @SerializedName("moves")
    val moveList: List<Moves>,

    @SerializedName("name")
    val name: String,

    @SerializedName("id")
    val id: Int,

    @SerializedName("weight")
    val weight: Int,

    @SerializedName("types")
    val typeList: List<Types>,

    @SerializedName("sprites")
    val image: Sprite,

    @SerializedName("height")
    val height: Int,

    @SerializedName("base_experience")
    val baseXp: Int,
)


data class Sprite(
    @SerializedName("front_default")
    val frontDefault: String

)

data class Types(
    @SerializedName("type")
    val type: Type
)

data class Type(
    @SerializedName("name")
    val name: String
)

data class Moves(
    @SerializedName("move")
    val move: Move
)


data class Move(
    @SerializedName("name")
    val name: String
)
