package com.akashi.pokedex.presentation.pokemondetail

import androidx.lifecycle.ViewModel
import com.akashi.pokedex.data.remote.responses.pokemon_info.Pokemon
import com.akashi.pokedex.data.remote.responses.pokemon_species.PokemonSpecies
import com.akashi.pokedex.repository.PokemonRepository
import com.akashi.pokedex.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PokemonDetailViewModel @Inject constructor(
    private val repository: PokemonRepository
): ViewModel() {

    suspend fun getPokemonInfo(pokemonName: String): Resource<Pokemon> {
        return repository.getPokemonInfo(pokemonName)
    }

    suspend fun getPokemonSpecies(pokemonName: String): Resource<PokemonSpecies> {
        return repository.getPokemonSpecies(pokemonName)
    }

}