package com.akashi.pokedex.repository

import com.akashi.pokedex.data.remote.PokeAPI
import com.akashi.pokedex.data.remote.responses.pokemon.PokemonList
import com.akashi.pokedex.data.remote.responses.pokemon_info.Pokemon
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class PokemonRepository @Inject constructor(
    private val api: PokeAPI
) {

}