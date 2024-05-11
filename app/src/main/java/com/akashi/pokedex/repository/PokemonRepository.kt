package com.akashi.pokedex.repository

import com.akashi.pokedex.data.remote.PokeAPI
import com.akashi.pokedex.data.remote.responses.pokemon.PokemonList
import com.akashi.pokedex.data.remote.responses.pokemon_info.Pokemon
import com.akashi.pokedex.data.remote.responses.pokemon_species.PokemonSpecies
import com.akashi.pokedex.utils.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeAPI
) {

    suspend fun getPokemonList(limit: Int = 100000, offset: Int = 0): Resource<PokemonList> {
        val response = try {
            api.getPokemonList(limit, offset)
        } catch (e: Exception) {
            return Resource.Error("An error occurred in fetching data.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        val response = try {
            api.getPokemonInfo(pokemonName)
        } catch (e: Exception) {
            return Resource.Error("An error occurred in fetching data.")
        }
        return Resource.Success(response)
    }

    suspend fun getPokemonSpecies(pokemonName: String): Resource<PokemonSpecies> {
        val response = try {
            api.getPokemonSpecies(pokemonName)
        } catch (e: Exception) {
            return Resource.Error("An error occurred in fetching data.")
        }
        return Resource.Success(response)
    }
}