package com.akashi.pokedex.data.remote.responses.pokemon_info

data class Move(
    val move: MoveX,
    val version_group_details: List<VersionGroupDetail>
)