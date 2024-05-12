package com.akashi.pokedex.data.remote.responses.pokemon_species


import com.google.gson.annotations.SerializedName

data class GrowthRate(
    @SerializedName("name")
    val name: String,
    @SerializedName("url")
    val url: String
)