package com.akashi.pokedex.data.remote.responses.pokemon_info


import com.google.gson.annotations.SerializedName

data class Gold(
    @SerializedName("back_default")
    val backDefault: String,
    @SerializedName("back_shiny")
    val backShiny: String,
    @SerializedName("front_default")
    val frontDefault: String,
    @SerializedName("front_shiny")
    val frontShiny: String,
    @SerializedName("front_transparent")
    val frontTransparent: String
)