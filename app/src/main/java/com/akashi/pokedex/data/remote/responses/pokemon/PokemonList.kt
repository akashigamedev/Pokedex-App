package com.akashi.pokedex.data.remote.responses.pokemon

import com.google.gson.annotations.SerializedName

data class PokemonList(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val next: String,
    @SerializedName("previous") val previous: Any,
    @SerializedName("results") val results: List<Result>
)