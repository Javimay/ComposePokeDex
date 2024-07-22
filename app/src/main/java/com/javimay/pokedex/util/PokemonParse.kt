package com.javimay.pokedex.util

import androidx.compose.ui.graphics.Color
import com.javimay.pokedex.data.remote.responses.Stat
import com.javimay.pokedex.data.remote.responses.Type
import com.javimay.pokedex.ui.theme.AtkColor
import com.javimay.pokedex.ui.theme.DefColor
import com.javimay.pokedex.ui.theme.HPColor
import com.javimay.pokedex.ui.theme.SpAtkColor
import com.javimay.pokedex.ui.theme.SpDefColor
import com.javimay.pokedex.ui.theme.SpdColor
import com.javimay.pokedex.ui.theme.TypeBug
import com.javimay.pokedex.ui.theme.TypeDark
import com.javimay.pokedex.ui.theme.TypeDragon
import com.javimay.pokedex.ui.theme.TypeElectric
import com.javimay.pokedex.ui.theme.TypeFairy
import com.javimay.pokedex.ui.theme.TypeFighting
import com.javimay.pokedex.ui.theme.TypeFire
import com.javimay.pokedex.ui.theme.TypeFlying
import com.javimay.pokedex.ui.theme.TypeGhost
import com.javimay.pokedex.ui.theme.TypeGrass
import com.javimay.pokedex.ui.theme.TypeGround
import com.javimay.pokedex.ui.theme.TypeIce
import com.javimay.pokedex.ui.theme.TypeNormal
import com.javimay.pokedex.ui.theme.TypePoison
import com.javimay.pokedex.ui.theme.TypePsychic
import com.javimay.pokedex.ui.theme.TypeRock
import com.javimay.pokedex.ui.theme.TypeSteel
import com.javimay.pokedex.ui.theme.TypeWater
import java.util.Locale

fun parseTypeToColor(type: Type): Color {
    return when (type.type.name.lowercase(Locale.ROOT)) {
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

    fun parseStatToColor(stat: Stat): Color {
        return when(stat.stat.name.lowercase(Locale.ROOT)) {
            "hp" -> HPColor
            "attack" -> AtkColor
            "defense" -> DefColor
            "special-attack" -> SpAtkColor
            "special-defense" -> SpDefColor
            "speed" -> SpdColor
            else -> Color.Black
        }
    }

    fun parseStatToAbbr(stat: Stat): String {
        return when(stat.stat.name.lowercase(Locale.ROOT)) {
            "hp" -> "HP"
            "attack" -> "Atk"
            "defense" -> "Def"
            "special-attack" -> "SpAtk"
            "special-defense" -> "SpDef"
            "speed" -> "Spd"
            else -> ""
        }
    }
