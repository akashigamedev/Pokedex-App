package com.akashi.pokedex.data.remote.responses.pokemon_species

data class FlavorTextEntry(
    val flavor_text: String,
    val language: Language,
    val version: Version
)