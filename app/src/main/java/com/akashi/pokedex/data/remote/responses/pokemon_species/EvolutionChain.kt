package com.akashi.pokedex.data.remote.responses.pokemon_species


import com.google.gson.annotations.SerializedName

data class EvolutionChain(
    @SerializedName("url")
    val url: String
)