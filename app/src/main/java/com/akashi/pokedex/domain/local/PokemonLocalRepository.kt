package com.akashi.pokedex.domain.local

import com.akashi.pokedex.data.model.SimplePokemon

interface PokemonLocalRepository {
    fun getPokemonList(): List<SimplePokemon>
    suspend fun insertPokemonList(pokemonList: List<SimplePokemon>)
}