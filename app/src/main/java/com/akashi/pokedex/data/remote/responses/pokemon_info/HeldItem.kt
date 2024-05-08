package com.akashi.pokedex.data.remote.responses.pokemon_info

data class HeldItem(
    val item: Item,
    val version_details: List<VersionDetail>
)