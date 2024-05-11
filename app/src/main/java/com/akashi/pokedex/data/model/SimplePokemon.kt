package com.akashi.pokedex.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SimplePokemon (
    @PrimaryKey val number: String,
    val name: String,
    val imageUrl: String,
)