package com.akashi.pokedex.utils

import androidx.compose.ui.graphics.Color
import com.akashi.pokedex.data.remote.responses.pokemon_info.Type
import com.akashi.pokedex.ui.theme.TypeBug
import com.akashi.pokedex.ui.theme.TypeDark
import com.akashi.pokedex.ui.theme.TypeDragon
import com.akashi.pokedex.ui.theme.TypeElectric
import com.akashi.pokedex.ui.theme.TypeFairy
import com.akashi.pokedex.ui.theme.TypeFighting
import com.akashi.pokedex.ui.theme.TypeFire
import com.akashi.pokedex.ui.theme.TypeFlying
import com.akashi.pokedex.ui.theme.TypeGhost
import com.akashi.pokedex.ui.theme.TypeGrass
import com.akashi.pokedex.ui.theme.TypeGround
import com.akashi.pokedex.ui.theme.TypeIce
import com.akashi.pokedex.ui.theme.TypeNormal
import com.akashi.pokedex.ui.theme.TypePoison
import com.akashi.pokedex.ui.theme.TypePsychic
import com.akashi.pokedex.ui.theme.TypeRock
import com.akashi.pokedex.ui.theme.TypeSteel
import com.akashi.pokedex.ui.theme.TypeWater
import java.util.Locale

fun parseTypeToColor(type: Type): Color {
    return when (type.type.name.toLowerCase(Locale.ROOT)) {
        "normal" -> TypeNormal
        "fire" -> TypeFire
        "water" -> TypeWater
        "electric" -> TypeElectric
        "grass" -> TypeGrass
        "ice" -> TypeIce
        "fighting" -> TypeFighting
        "poison" -> TypePoison
        "ground" -> TypeGround
        "flying" -> TypeFlying
        "psychic" -> TypePsychic
        "bug" -> TypeBug
        "rock" -> TypeRock
        "ghost" -> TypeGhost
        "dragon" -> TypeDragon
        "dark" -> TypeDark
        "steel" -> TypeSteel
        "fairy" -> TypeFairy
        else -> Color.Black
    }
}