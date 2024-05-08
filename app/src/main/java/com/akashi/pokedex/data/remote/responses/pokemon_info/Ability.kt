package com.akashi.pokedex.data.remote.responses.pokemon_info

data class Ability(
    val ability: AbilityX,
    val is_hidden: Boolean,
    val slot: Int
)