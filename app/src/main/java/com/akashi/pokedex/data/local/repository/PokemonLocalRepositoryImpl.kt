package com.akashi.pokedex.data.local.repository

import com.akashi.pokedex.data.local.PokemonDao
import com.akashi.pokedex.data.model.SimplePokemon
import com.akashi.pokedex.domain.local.PokemonLocalRepository

class PokemonLocalRepositoryImpl(
    private val dao: PokemonDao
) : PokemonLocalRepository{
    override fun getPokemonList(): List<SimplePokemon> {
        return dao.getPokemonList()
    }

    override suspend fun insertPokemonList(pokemonList: List<SimplePokemon>) {
        dao.insertPokemonList(pokemonList)
    }
}