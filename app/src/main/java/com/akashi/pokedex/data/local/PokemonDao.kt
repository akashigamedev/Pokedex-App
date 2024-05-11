package com.akashi.pokedex.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.akashi.pokedex.data.model.SimplePokemon

@Dao
interface PokemonDao {
    @Query("SELECT * from SimplePokemon")
    fun getPokemonList(): List<SimplePokemon>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPokemonList(pokemonList: List<SimplePokemon>)
}