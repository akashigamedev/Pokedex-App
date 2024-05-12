package com.akashi.pokedex.data.remote.responses.pokemon_info


import com.google.gson.annotations.SerializedName

data class GenerationV(
    @SerializedName("black-white")
    val blackWhite: BlackWhite
)