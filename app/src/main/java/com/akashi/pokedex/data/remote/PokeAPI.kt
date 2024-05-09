package com.akashi.pokedex.data.remote

import com.akashi.pokedex.data.remote.responses.pokemon.PokemonList
import com.akashi.pokedex.data.remote.responses.pokemon_info.Pokemon
import com.akashi.pokedex.data.remote.responses.pokemon_species.PokemonSpecies
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokeAPI {
    @GET("pokemon")
    suspend fun getPokemonList(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonList

    @GET("pokemon/{name}")
    suspend fun getPokemonInfo(
        @Path("name") name: String
    ): Pokemon

    @GET("pokemon-species/{name}")
    suspend fun getPokemonSpecies(
        @Path("name") name: String
    ): PokemonSpecies
}