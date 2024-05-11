package com.akashi.pokedex.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.akashi.pokedex.data.model.SimplePokemon

@Database(
    entities = [SimplePokemon::class],
    version = 1
)
abstract class PokemonDatabase : RoomDatabase() {
    abstract val pokemonDao: PokemonDao

    companion object {
        const val DATABASE_NAME = "pokemon_db"
    }
}