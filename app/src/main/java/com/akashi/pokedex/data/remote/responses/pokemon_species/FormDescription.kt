package com.akashi.pokedex.data.remote.responses.pokemon_species


import com.google.gson.annotations.SerializedName

data class FormDescription(
    @SerializedName("description")
    val description: String,
    @SerializedName("language")
    val language: Language
)