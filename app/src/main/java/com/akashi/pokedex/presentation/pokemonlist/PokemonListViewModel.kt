package com.akashi.pokedex.presentation.pokemonlist

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.akashi.pokedex.data.model.SimplePokemon
import com.akashi.pokedex.domain.local.PokemonLocalRepository
import com.akashi.pokedex.repository.PokemonRepository
import com.akashi.pokedex.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PokemonListViewModel @Inject constructor(
    private val repository: PokemonRepository,
    private val localRepository: PokemonLocalRepository
) : ViewModel() {


    var pokemonList = mutableStateOf<List<SimplePokemon>>(listOf())
    var loadError = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var cachedPokemonList = listOf<SimplePokemon>()
    private var readyToSearch = true

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            val localPokemonList = localRepository.getPokemonList()
            if (localPokemonList.isEmpty()) {
                fetchPokemonListFromAPI()
            } else {
                pokemonList.value = localPokemonList
            }
        }
    }

    private fun fetchPokemonListFromAPI() {
        viewModelScope.launch {
            isLoading.value = true
            when (val result = repository.getPokemonList()) {
                is Resource.Success -> {
                    val pokedexEntries = result.data!!.results.mapIndexed { _, pokemon ->
                        val number = if (pokemon.url.endsWith("/")) {
                            pokemon.url.dropLast(1).takeLastWhile { it.isDigit() }
                        } else {
                            pokemon.url.takeLastWhile { it.isDigit() }
                        }
                        val url =
                            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${number}.png"
                        SimplePokemon(
                            number = number.toInt(),
                            name = pokemon.name.replaceFirstChar { it.uppercase() },
                            imageUrl = url
                        )
                    }
                    localRepository.insertPokemonList(pokedexEntries)
                    pokemonList.value = pokedexEntries
                    loadError.value = ""
                    isLoading.value = false
                }

                is Resource.Loading -> {
                    // implement something in future
                }

                is Resource.Error -> {
                    loadError.value = result.message!!
                    isLoading.value = false
                }
            }
        }
    }

    fun searchPokemonList(query: String) {
        val listToSearch = if (readyToSearch) {
            pokemonList.value
        } else {
            cachedPokemonList
        }
        viewModelScope.launch(Dispatchers.Default) {
            if (query.isBlank() || query.isEmpty()) {
                pokemonList.value = cachedPokemonList
                readyToSearch = true
                return@launch
            }
            val results = listToSearch.filter {
                it.name.contains(query.trim(), ignoreCase = true) ||
                        it.number.toString() == query.trim() ||
                        it.number.toString().padStart(3, '0') == query.trim()
            }
            if (readyToSearch) {
                cachedPokemonList = pokemonList.value
                readyToSearch = false
            }
            pokemonList.value = results
        }
    }
}